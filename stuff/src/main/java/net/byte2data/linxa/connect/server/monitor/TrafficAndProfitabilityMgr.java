package net.byte2data.linxa.connect.server.monitor;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Month;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import com.linxa.connect.server.monitor.AlertUtil;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.asimalp.commons.server.email.HtmlEmail;
import com.asimalp.commons.server.email.SmtpProperties;
import com.asimalp.commons.server.sql.SqlText;
import com.asimalp.commons.server.util.Chrono;
import com.asimalp.commons.server.util.CsvUtil;
import com.asimalp.commons.server.util.DateUtil;
import com.asimalp.commons.server.util.ExcelStyleType;
import com.asimalp.commons.server.util.ExcelUtil;
import com.asimalp.commons.server.util.Log;
import com.asimalp.commons.server.util.StringUtil;
import com.asimalp.commons.shared.model.CommonPrivilege;
import com.asimalp.commons.shared.model.Country;
import com.asimalp.commons.shared.model.Currency;
import com.asimalp.commons.shared.model.CustomReportExportType;
import com.asimalp.commons.shared.model.DateRange;
import com.asimalp.commons.shared.model.ExportResult;
import com.asimalp.commons.shared.model.LocalUser;
import com.asimalp.commons.shared.model.PartialList;
import com.asimalp.commons.shared.model.Privilege;
import com.asimalp.commons.shared.model.Range;
import com.asimalp.commons.shared.model.SafeDate;
import com.asimalp.commons.shared.model.Table;
import com.asimalp.commons.shared.model.TableRow;
import com.asimalp.commons.shared.model.User;
import com.asimalp.commons.shared.throwable.EarlyBreakException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.linxa.connect.client.activity.monitor.TrafficAndProfitabilityWidgetActivity;
import com.linxa.connect.server.SF;
import com.linxa.connect.server.i18n.AggregatedDataTypeLanguage;
import com.linxa.connect.server.i18n.QualityStoreDataTypeLanguage;
import com.linxa.connect.server.i18n.ReportPresetFilterTypeLanguage;
import com.linxa.connect.server.nio.AggregatedDataRow;
import com.linxa.connect.server.nio.AggregatedDataStore;
import com.linxa.connect.server.nio.Cdr;
import com.linxa.connect.server.nio.CdrReference;
import com.linxa.connect.server.nio.CdrStoreParser;
import com.linxa.connect.server.nio.NF;
import com.linxa.connect.server.nio.Ni;
import com.linxa.connect.server.nio.SwitchNameProvider;
import com.linxa.connect.server.nio.quality.QualityData;
import com.linxa.connect.server.nio.quality.QualityStore;
import com.linxa.connect.server.route.PrefixGroups;
import com.linxa.connect.server.route.ReverseNameLookup;
import com.linxa.connect.shared.model.AggregateDataColumn;
import com.linxa.connect.shared.model.AggregatedDataGridRow;
import com.linxa.connect.shared.model.AggregatedDataType;
import com.linxa.connect.shared.model.Carrier;
import com.linxa.connect.shared.model.CarrierGroup;
import com.linxa.connect.shared.model.CdrAdvancedQueryResult;
import com.linxa.connect.shared.model.CdrQueryParameters;
import com.linxa.connect.shared.model.CdrSource;
import com.linxa.connect.shared.model.ConnectPrivilege;
import com.linxa.connect.shared.model.DatePeriodType;
import com.linxa.connect.shared.model.Day;
import com.linxa.connect.shared.model.DestinationCategory;
import com.linxa.connect.shared.model.Kpi;
import com.linxa.connect.shared.model.NoType;
import com.linxa.connect.shared.model.OperationLogType;
import com.linxa.connect.shared.model.Order;
import com.linxa.connect.shared.model.PgItem;
import com.linxa.connect.shared.model.PgSelector;
import com.linxa.connect.shared.model.ProfitabilityExchangeRateType;
import com.linxa.connect.shared.model.QualityStoreDataType;
import com.linxa.connect.shared.model.QueryResult;
import com.linxa.connect.shared.model.ReportPreset;
import com.linxa.connect.shared.model.ReportPresetFilterType;
import com.linxa.connect.shared.model.Route;
import com.linxa.connect.shared.model.RoutingDecisionPrefixes;
import com.linxa.connect.shared.model.SmtpSettingType;
import com.linxa.connect.shared.model.TargetRouteType;
import com.linxa.connect.shared.model.Time;
import com.linxa.connect.shared.model.TrafficAndProfitabilityWidgetParameters;
import com.linxa.connect.shared.model.TrafficDirection;
import com.linxa.connect.shared.model.TrafficFormat;
import com.linxa.connect.shared.model.TrafficTrendsResolution;
import com.linxa.connect.shared.model.TrendDisplayOptionType;
import com.linxa.connect.shared.model.TrunkAndIpSets;
import com.linxa.connect.shared.quality.QualityDataRow;

public class TrafficAndProfitabilityMgr {

    private static final TrafficAndProfitabilityMgr SINGLETON = new TrafficAndProfitabilityMgr();

    private Cache<Integer, ArrayList<CdrSource>> cdrSourceCache = CacheBuilder.newBuilder().softValues().expireAfterWrite(15, TimeUnit.MINUTES).build();

    private Cache<String, LinkedHashMap<SafeDate, ConcurrentHashMap<String, AggregatedDataGridRow>>> cache = CacheBuilder.newBuilder().softValues()
            .expireAfterWrite(15, TimeUnit.MINUTES).build();

    private Cache<String, LinkedHashMap<SafeDate, ConcurrentHashMap<String, AggregatedDataGridRow>>> dashboardWidgetcache = CacheBuilder.newBuilder().build();

    private HashSet<String> cancelledReportKeys = new HashSet<>();

    private HashMap<Integer, DateTime> dailyDateTime = new HashMap<>();

    private Cache<String, Double> averageExchangeRatesCache = CacheBuilder.newBuilder().softValues().build();

    private Log debugLog;

    private HashMap<String, Object> cacheLocks = new HashMap<>();

    private Timer dashboardWidgetCacheTimer = new Timer(getClass().getName() + ".dashboardWidgetCacheTimer");

    public static TrafficAndProfitabilityMgr getInstance() {
        return SINGLETON;
    }

    private TrafficAndProfitabilityMgr() {
        System.out.println("TrafficAndProfitabilityMgr is being constructed...");
        debugLog = new Log("trafficAndProfitability");

        DateTime firstExecutionTime = new DateTime().withTimeAtStartOfDay().withHourOfDay(new DateTime().getHourOfDay()).plusHours(1);
        dashboardWidgetCacheTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                refreshDashboardWidgetCache();
            }
        }, firstExecutionTime.toDate(), DateUtil.HOUR);
        System.out.println("TrafficAndProfitabilityMgr construction completed.");
    }

    public void kill() {
        SF.l(getClass(), "kill");
        dashboardWidgetCacheTimer.cancel();
    }

    // TODO-Emre change to private
    public void refreshDashboardWidgetCache() {
        Object timer = Chrono.start();
        Table table = SF.p()
                .q("SELECT CID, ParametersJOID FROM ConnectDashboardWidget WHERE ActivityClass='" + TrafficAndProfitabilityWidgetActivity.class.getName() + "'");
        SF.l(this.getClass(), "refreshDashboardWidgetCache, count:" + table.rowCount());
        HashSet<String> addedKeys = new HashSet<>();
        int numberOfDuplicateWidgets = 0;
        for (TableRow row : table) {
            int cid = row.getInteger("CID");
            int joid = row.getInteger("ParametersJOID");

            TrafficAndProfitabilityWidgetParameters parameters = (TrafficAndProfitabilityWidgetParameters) SF.p().o().r(joid);
            String gridKey = parameters.getKeyWithoutId();
            if (addedKeys.contains(gridKey)) {
                numberOfDuplicateWidgets++;
                continue;
            }

            LinkedList<AggregateDataColumn> columns = parameters.getPreset().getColumnList();
            Order[] order = new Order[columns.size()];
            try {
                int i = 0;
                for (AggregateDataColumn col : columns) {
                    Order o = new Order(col.getColumnType().name(), true);
                    order[i++] = o;

                }
            } catch (Exception e) {
                order = new Order[0];
            }

            LinkedHashMap<SafeDate, ConcurrentHashMap<String, AggregatedDataGridRow>> rows = getRows(cid, parameters.getFrom(), parameters.getTo(),
                    parameters.getPreset(), null, order, 0, "127.0.0.1");

            synchronized (getCacheLock(gridKey)) {
                dashboardWidgetcache.put(gridKey, rows);
                addedKeys.add(gridKey);
            }
        }

        HashSet<String> currentKeys = new HashSet<>(dashboardWidgetcache.asMap().keySet());
        for (String currentKey : currentKeys) {
            if (!addedKeys.contains(currentKey)) {
                dashboardWidgetcache.invalidate(currentKey);
            }
        }
        SF.l(this.getClass(), "Completed refreshDashboardWidgetCache in " + Chrono.stop(timer) + ". Number of duplicate widgets:" + numberOfDuplicateWidgets);
    }

    public LinkedList<AggregateDataColumn> getColumnList(int cid, int uid, boolean isWidget) {

        LocalUser user = SF.user().getUser(uid);
        LinkedList<AggregateDataColumn> columns = new LinkedList<>();
        for (AggregatedDataType aggregateDataType : AggregatedDataType.values()) {
            if (aggregateDataType.isColumn()) {
                if ((aggregateDataType == AggregatedDataType.OUTSEIZED_ORIGIN || aggregateDataType == AggregatedDataType.OUTSIEZED)
                        && !SF.d().isOutseizeNumberEnabled(cid)) {
                    continue;
                }
                if (isWidget && !aggregateDataType.isAllowedInWidget()) {
                    continue;
                }

                boolean isAllowed = false;
                ConnectPrivilege[] dataTypePriv = aggregateDataType.getPriviledges();
                if (dataTypePriv != null) {
                    for (ConnectPrivilege privilege : dataTypePriv) {

                        HashSet<Privilege> userPrivileges = user.getPrivileges();
                        isAllowed |= userPrivileges.contains(privilege);
                    }
                }

                if (isAllowed) {
                    String columnName = AggregatedDataTypeLanguage.get(cid, aggregateDataType);
                    columns.add(new AggregateDataColumn(columnName, aggregateDataType));
                }
            }
        }

        QualityStore qualityStore = NF.no(cid).getQualityStore();
        if (qualityStore != null) {
            List<QualityStoreDataType> qualityTypes = qualityStore.getAvailableQualityTypes();
            for (QualityStoreDataType type : qualityTypes) {
                if (!type.isAllowedInReport()) {
                    continue;
                }
                String columnName = QualityStoreDataTypeLanguage.get(cid, type);
                columns.add(new AggregateDataColumn(columnName, type));
            }
        }
        return columns;
    }

    public int savePreset(int cid, int uid, String ip, ReportPreset preset) {
        int crpid;
        preset.setUid(uid);
        preset.setShowingCdr(false); // dont save reports as showing cdr.
        if (preset.getJoid() != null) {
            // Update preset
            crpid = preset.getCrpid();
            SF.p().o().u(preset.getJoid(), preset);
            SF.p().u("ConnectCustomReportPreset", "PresetName", preset.getPresetName(), "IsGlobal", preset.isGlobal(), "CID", cid, "UID",
                    preset.isGlobal() ? new SqlText("NULL") : uid, "TSUpdate", new SqlText("NOW()"), "CCRPID=" + crpid);
        } else {
            // Saving new preset
            int joid = SF.p().o().w(preset);
            crpid = SF.p().iid("ConnectCustomReportPreset", "PresetName", preset.getPresetName(), "IsGlobal", preset.isGlobal(), "CID", cid, "UID",
                    preset.isGlobal() ? new SqlText("NULL") : uid, "TSUpdate", new SqlText("NOW()"), "PresetJOID", joid);
        }
        String log = getSaveLog(preset, cid);
        SF.l().addLog(cid, uid, ip, null, OperationLogType.SAVE_PRESET, log);

        return crpid;
    }

    private String getSaveLog(ReportPreset preset, int cid) {
        String log;
        StringBuilder sb = new StringBuilder();
        sb.append("\n- Preset Name: " + preset.getPresetName());
        sb.append("\n- Visible to everyone: " + preset.isGlobal());
        LinkedList<AggregateDataColumn> columns = preset.getColumnList();
        if (columns != null && columns.size() > 0) {
            sb.append("- Columns: ");
            for (int i = 0; i < columns.size(); i++) {
                AggregateDataColumn adc = columns.get(i);
                sb.append(adc.getName());
                if (i + 1 != columns.size()) {
                    sb.append(", ");
                }
            }
        }

        if (preset.getFilters() != null && preset.getFilters().size() > 0) {
            sb.append("\n- Filters:\n");
            for (Entry<ReportPresetFilterType, String> entry : preset.getFilters().entrySet()) {
                ReportPresetFilterType filterType = entry.getKey();
                if (filterType == ReportPresetFilterType.PERIOD_PRESET) {
                    continue;
                }
                sb.append("\t" + ReportPresetFilterTypeLanguage.get(cid, filterType) + ": " + entry.getValue() + "\n");
            }
        }

        log = sb.toString();
        return log;
    }

    public LinkedHashMap<Integer, ReportPreset> getPresetList(int cid, int uid) {
        LinkedHashMap<Integer, ReportPreset> presets = new LinkedHashMap<>();
        LocalUser user = SF.user().getUser(uid);
        boolean isTechSupport = user.getPrivileges().contains(CommonPrivilege.TECH_SUPPORT);
        String query = "SELECT * FROM ConnectCustomReportPreset WHERE CID=" + cid + " AND (UID=" + uid + " OR (UID IS NULL AND IsGlobal=1)) ORDER BY PresetName";
        Table table = SF.p().q(query);
        for (final TableRow row : table) {
            int joid = row.getInteger("PresetJOID");
            boolean isGlobal = row.getBoolean("IsGlobal");
            int crpid = row.getInteger("CCRPID");
            ReportPreset preset = SF.p().o().r(joid);
            preset.setGlobal(isGlobal);
            preset.setCrpid(crpid);
            preset.setJoid(joid);
            SafeDate updateDate = new SafeDate(row.getDateTime("TSUpdate"));
            preset.setTsUpdate(updateDate);
            preset.setUid(row.getInteger("UID"));

            // filter accordingly with privileges and columns
            boolean isAllowed = true;
            if (!isTechSupport) {
                HashSet<Privilege> userPrivileges = user.getPrivileges();
                isAllowed = preset.userCanViewPreset(userPrivileges);
            }
            if (isAllowed) {
                presets.put(crpid, preset);
            }
        }
        return presets;
    }

    public ReportPreset getPreset(int cid, int crpid) {
        ReportPreset preset = null;
        int presetJoid = SF.p().q("SELECT PresetJOID FROM ConnectCustomReportPreset where CCRPID=" + crpid).getInteger(0, 0);
        preset = SF.p().o().r(presetJoid);
        preset.setCrpid(crpid);
        return preset;
    }

    public void deletePreset(int crpid, int uid, int cid, String ip) {
        Table info = SF.p().q("SELECT PresetJOID,PresetName FROM ConnectCustomReportPreset where CCRPID=" + crpid);
        int presetJoid = info.getInteger(0, 0);
        String presetName = info.get(0, 1);
        SF.p().o().bd(presetJoid);
        SF.p().d("ConnectCustomReportPreset", "CCRPID=" + crpid);

        SF.l().addLog(cid, uid, ip, null, OperationLogType.DELETE_PRESET, "\n- Traffic and Profitability Preset Name: " + presetName);

    }

    public LinkedHashMap<SafeDate, ConcurrentHashMap<String, AggregatedDataGridRow>> getRows(int cid, SafeDate from, SafeDate to, ReportPreset reportPreset,
                                                                                             Range pagingRange, Order[] sortOrder, int uid, String ip) {
        // TODO-Emre Timers for logs
        // long initialDataTime = 0;
        final SafeDate originalTo;

        boolean hasOutgoingSorting = false;
        LinkedList<AggregateDataColumn> columns = reportPreset.getColumnList();

        for (AggregateDataColumn col : columns) {
            AggregatedDataType columnType = col.getColumnType();
            if (columnType == AggregatedDataType.OUTGOING_CARRIER || columnType == AggregatedDataType.OUTGOING_TRUNK || columnType == AggregatedDataType.OUTGOING_SWITCH
                    || columnType == AggregatedDataType.OUTGOING_CARRIER_GROUP) {
                hasOutgoingSorting = true;
                break;
            }
        }
        final boolean hasOutSorting = hasOutgoingSorting;
        SwitchNameProvider snp = NF.no(cid).getSwitchNameProvider();

        String presetKey = reportPreset.getKey();
        String cancelKey = uid + "-" + presetKey;
        AtomicBoolean lastTimeCorrected = new AtomicBoolean(false);
        if (to.isAfter(new DateTime())) {
            int minuteOfDay = new DateTime().getMinuteOfDay();
            minuteOfDay = minuteOfDay - (minuteOfDay % 5) + 5;
            to = new SafeDate(new DateTime().withTimeAtStartOfDay().plusMinutes(minuteOfDay));
            lastTimeCorrected.set(true);
        }

        DateTime cdrEpoch = SF.d().getCdrEpoch(cid);
        if (from.isBefore(cdrEpoch)) {
            from = new SafeDate(cdrEpoch);
        }

        // Time zone correction
        String timeZoneId = reportPreset.getFilters().get(ReportPresetFilterType.TIMEZONE);
        if (!StringUtil.isBlank(timeZoneId)) {
            from = new SafeDate(DateUtil.overrideTimeZone(from.toDate(), timeZoneId));
            to = new SafeDate(DateUtil.overrideTimeZone(to.toDate(), timeZoneId));
            originalTo = new SafeDate(DateUtil.overrideTimeZone(to.toDate(), timeZoneId));
        } else {
            originalTo = to.clone();
        }

        ExecutorService executor = Executors.newFixedThreadPool(SF.d().getTrafficAndProfitabilityExecutorThreadCount());

        boolean includeStatusCodes = reportPreset.hasColumn(AggregatedDataType.STATUS_CODES)
                || reportPreset.getFilters().containsKey(ReportPresetFilterType.STATUS_CODES);
        final boolean includeInDeal = reportPreset.hasColumn(AggregatedDataType.INCOMING_BILATERAL_DEAL)
                || reportPreset.getFilters().containsKey(ReportPresetFilterType.INCOMING_BILATERAL_DEAL);
        final boolean includeOutDeal = reportPreset.hasColumn(AggregatedDataType.OUTGOING_BILATERAL_DEAL)
                || reportPreset.getFilters().containsKey(ReportPresetFilterType.OUTGOING_BILATERAL_DEAL);
        final boolean isSplit = reportPreset.hasColumn(AggregatedDataType.CHARGE_TYPE) || reportPreset.hasColumn(AggregatedDataType.TRAFFIC_DIRECTION);
        boolean includeRoutingDecision = reportPreset.hasColumn(AggregatedDataType.ROUTING_DECISION)
                || reportPreset.getFilters().containsKey(ReportPresetFilterType.ROUTING_DECISION);

        final HashMap<ReportPresetFilterType, String> filters = new HashMap<>();
        for (Entry<ReportPresetFilterType, String> filterEntry : reportPreset.getFilters().entrySet()) {
            if (filterEntry.getKey() != ReportPresetFilterType.TO && filterEntry.getKey() != ReportPresetFilterType.FROM
                    && filterEntry.getKey() != ReportPresetFilterType.CURRENCY && filterEntry.getKey() != ReportPresetFilterType.PERIOD_PRESET
                    && filterEntry.getKey() != ReportPresetFilterType.INCOMING_CARRIER && filterEntry.getKey() != ReportPresetFilterType.OUTGOING_CARRIER) {
                filters.put(filterEntry.getKey(), filterEntry.getValue());
            }
        }
        final boolean hasFilters = filters.size() > 0;
        final boolean hasQualityData = reportPreset.hasQualityRelatedColumn();
        ArrayList<DateRange> trendRanges = getRanges(from.toDateTime(), to.toDateTime(), reportPreset);
        LinkedHashMap<SafeDate, ConcurrentHashMap<String, AggregatedDataGridRow>> allRows = new LinkedHashMap<>();
        HashMap<String, AggregatedDataGridRow> uniqueKeys = new HashMap<>();
        AggregatedDataStore aggregatedDataStore = NF.no(cid).getAggregatedDataStore();

        String exchangeRateTypeStr = reportPreset.getFilters().get(ReportPresetFilterType.EXCHANGE_RATE);
        ProfitabilityExchangeRateType exchangeRateType = ProfitabilityExchangeRateType.MONTHLY;
        if (!StringUtil.isBlank(exchangeRateTypeStr)) {
            try {
                exchangeRateType = ProfitabilityExchangeRateType.getType(exchangeRateTypeStr);
            } catch (Exception e) {
            }
        }

        dailyDateTime = new HashMap<>();
        DateRange originalRange = new DateRange(from, to);
        calculateExchangeRateDates(originalRange, exchangeRateType);

        // get necessary data from other managers
        LinkedHashMap<Integer, Carrier> carriers = SF.carrier().getCarriers(cid, null, true, true, true, true, true, "");
        Carrier unknownCarrier = SF.carrier().getUnknownCarrier(cid);
        LinkedHashMap<Integer, CarrierGroup> carrierGroups = SF.carrier().getCarrierGroups(cid, true);
        LinkedHashMap<Integer, LocalUser> users = SF.user().getUsers(cid, true, true, true);
        LinkedHashMap<Integer, User> routingManagers = SF.routingManager().getRoutingManagers(cid);
        HashMap<Integer, Route> routeMap = new HashMap<>();
        for (Route r : SF.routing().getRouteList(cid, true, false)) {
            routeMap.put(r.getCrid(), r);
        }

        LinkedHashMap<Integer, HashSet<Country>> routingManagersCountriesMap = SF.routingManager().getRoutingManagersCountriesMap(cid);
        HashMap<String, Integer> countriesToRoutingManagersMap = new HashMap<>();
        for (Entry<Integer, HashSet<Country>> entry : routingManagersCountriesMap.entrySet()) {
            Integer rmId = entry.getKey();
            for (Country country : entry.getValue()) {
                countriesToRoutingManagersMap.put(country.getEnglishShortName().toUpperCase(), rmId);
            }
        }
        //Determine exchange rate handling
        Currency reportCurrency = SF.d().getDefaultCurrency(cid);
        try {
            reportCurrency = StringUtil.isBlank(reportPreset.getFilters().get(ReportPresetFilterType.CURRENCY)) ? SF.d().getDefaultCurrency(cid)
                    : Currency.getByName(reportPreset.getFilters().get(ReportPresetFilterType.CURRENCY));
        } catch (Exception e) {
            e.printStackTrace();
            SF.l(getClass(), cid, e);
            return null;
        }

        HashSet<Integer> inFilterCcids = getFilterCcids(carriers, carrierGroups, reportPreset.getFilters().get(ReportPresetFilterType.INCOMING_CARRIER));
        HashSet<Integer> outFilterCcids = getFilterCcids(carriers, carrierGroups, reportPreset.getFilters().get(ReportPresetFilterType.OUTGOING_CARRIER));

        final HashMap<Integer, String> accMgrMap;
        if (!StringUtil.isEmpty(reportPreset.getFilters().get(ReportPresetFilterType.INCOMING_ACCOUNT_MANAGER))
                || !StringUtil.isEmpty(reportPreset.getFilters().get(ReportPresetFilterType.OUTGOING_ACCOUNT_MANAGER))
                || !StringUtil.isEmpty(reportPreset.getFilters().get(ReportPresetFilterType.ACCOUNT_MANAGER))) {
            accMgrMap = createAccountMgrMap(carriers, users);
        } else {
            accMgrMap = null;
        }

        boolean isOutseizeNumberEnabled = SF.d().isOutseizeNumberEnabled(cid);
        // TODO-Emre
        //initialDataTime += Chrono.stop(initialDataChrono);

        HashMap<Integer, String> accountMgrs = new HashMap<>();
        for (Carrier carrier : carriers.values()) {
            String accountManagerName = "";
            if (carrier != null && users.size() > 0) {
                Integer accountManagerId = carrier.getAccountManager();
                if (accountManagerId != -1) {
                    LocalUser localUser = users.get(accountManagerId);
                    if (localUser != null) {
                        accountManagerName = localUser.getDisplayName();
                    }
                }
            }
            accountMgrs.put(carrier.getCcid(), accountManagerName);
        }

        HashMap<SafeDate, QualityData> qualityDataMap = new HashMap<>();

        for (int j = 0; j < trendRanges.size(); j++) {
            final DateRange range = trendRanges.get(j);

            final Integer jValue = j;
            PrefixGroups prefixGroups = SF.prefixGroups().getPrefixGroups(cid, range.getTo().minusDays(1));
            ArrayList<PgItem> regions = new ArrayList<>();
            prefixGroups.getNames(null, PgSelector.ADD_REGION).forEach(name -> {
                regions.add(prefixGroups.get(name));
            });
            HashMap<String, String> destinationToRegionMap = new HashMap<>();
            for (PgItem region : regions) {
                for (String dest : region.getSubDestinations()) {
                    destinationToRegionMap.put(dest, region.getName());
                }
            }

            // Prepare time consuming filter data here in order to reduce time when checking aggregated data rows
            HashSet<String> matchingDestinations = StringUtil.isEmpty(reportPreset.getFilters().get(ReportPresetFilterType.DESTINATION)) ? null
                    : new HashSet<>(prefixGroups.getNames(reportPreset.getFilters().get(ReportPresetFilterType.DESTINATION), true, PgSelector.ADD_REFERENCE_DESTINATION,
                    PgSelector.ADD_UNKNOWN, PgSelector.ADD_DATABASE_DRIVEN_DESTINATION, PgSelector.REMOVE_DO_NOT_USE_IN_REPORTS));
            HashSet<String> matchingOrigins = StringUtil.isEmpty(reportPreset.getFilters().get(ReportPresetFilterType.ORIGIN)) ? null
                    : new HashSet<>(prefixGroups.getNames(reportPreset.getFilters().get(ReportPresetFilterType.ORIGIN), true, PgSelector.ADD_REFERENCE_DESTINATION,
                    PgSelector.ADD_REFERENCE_ORIGIN, PgSelector.ADD_UNKNOWN, PgSelector.ADD_DATABASE_DRIVEN_DESTINATION,
                    PgSelector.REMOVE_DO_NOT_USE_IN_REPORTS));

            ConcurrentHashMap<String, AggregatedDataGridRow> rowMap = new ConcurrentHashMap<>();
            ArrayList<DateRange> exchangeDailyRanges = getExchangeRanges(range, exchangeRateType);

            // Generate destination to country names map
            HashMap<String, String> destToCountryMap = new HashMap<>();
            SortedSet<String> destinationNames = prefixGroups.getNames(null, PgSelector.ADD_REFERENCE_DESTINATION, PgSelector.ADD_REFERENCE_DESTINATION,
                    PgSelector.ADD_UNKNOWN, PgSelector.ADD_DATABASE_DRIVEN_DESTINATION, PgSelector.REMOVE_DO_NOT_USE_IN_REPORTS);

            for (String destination : destinationNames) {
                String country = null;
                try {
                    for (Country c : prefixGroups.getCountriesRight(destination)) {
                        country = c.getEnglishShortName();
                        break;
                    }
                } catch (Exception e) {
                    country = Country.UNKNOWN.getEnglishShortName();
                }
                destToCountryMap.put(destination, country != null ? country.toUpperCase() : Country.UNKNOWN.getEnglishShortName());
            }

            for (DateRange exchangeRange : exchangeDailyRanges) {
                HashMap<Currency, Double> exchangeRatesMap = new HashMap<>();
                for (Currency currency : SF.exchange().getExchangeRates(cid, reportCurrency).keySet()) {
                    exchangeRatesMap.put(currency, getExchangeRate(cid, exchangeRateType, exchangeRange, originalRange, currency, reportCurrency));
                }

                ArrayList<List<AggregatedDataRow>> data = new ArrayList<>();
                try {
                    data = aggregatedDataStore.getData(exchangeRange, inFilterCcids, outFilterCcids);
                } catch (IOException | EarlyBreakException e) {
                    SF.l(this.getClass(), cid, e);
                }

                if (hasQualityData) {
                    QualityData qualityData = NF.no(cid).getQualityStore().getQualityData(range, inFilterCcids, outFilterCcids, matchingDestinations, matchingOrigins);
                    qualityDataMap.put(exchangeRange.getFrom(), qualityData);
                }

                LinkedList<Future<?>> futures = new LinkedList<>();
                for (List<AggregatedDataRow> list : data) {
                    futures.add(executor.submit(new Runnable() {

                        @Override
                        public void run() {
                            for (AggregatedDataRow dataRow : list) {

                                if (cancelledReportKeys.contains(cancelKey)) {
                                    break;
                                }

                                Carrier inCarrier = carriers.get(dataRow.getInCcid());
                                Carrier outCarrier = carriers.get(dataRow.getOutCcid());
                                CarrierGroup inCarrierGroup = null;
                                if (inCarrier != null && inCarrier.getCcgid() != null) {
                                    inCarrierGroup = carrierGroups.get(inCarrier.getCcgid());
                                }

                                CarrierGroup outCarrierGroup = null;
                                if (outCarrier != null && outCarrier.getCcgid() != null) {
                                    outCarrierGroup = carrierGroups.get(outCarrier.getCcgid());
                                }

                                Route route = routeMap.get(dataRow.getCrid());

                                String inCarrierName = inCarrier == null ? unknownCarrier.getName() : inCarrier.getName();
                                String outCarrierName = outCarrier == null ? unknownCarrier.getName() : outCarrier.getName();
                                String inCarrierGroupName = inCarrierGroup == null ? "" : inCarrierGroup.getName();
                                String outCarrierGroupName = outCarrierGroup == null ? "" : outCarrierGroup.getName();

                                String destination = dataRow.getDestination();
                                PgItem dstPgItem = prefixGroups.get(destination);
                                String origin = dataRow.getOrigin();
                                PgItem orgPgItem = prefixGroups.get(origin);

                                String destinationCountry = destToCountryMap.get(destination);
                                String originCountry = destToCountryMap.get(origin);

                                String destinationRegion = destinationToRegionMap.get(destination);
                                String originRegion = destinationToRegionMap.get(origin);

                                String routingClassName = route == null ? null : route.getTitle();
                                Integer countryRoutingManagerId = countriesToRoutingManagersMap.get(destinationCountry);

                                String inAccMgr = accountMgrs.get(inCarrier.getCcid());
                                String outAccMgr = accountMgrs.get(outCarrier.getCcid());

                                String countryRoutingManagerName = null;
                                if (countryRoutingManagerId != null) {
                                    LocalUser countryRoutingManager = users.get(countryRoutingManagerId);
                                    countryRoutingManagerName = countryRoutingManager == null ? null : countryRoutingManager.getDisplayName();
                                }

                                DestinationCategory destinationCategory = getCategory(dstPgItem, prefixGroups);
                                DestinationCategory originCategory = getCategory(orgPgItem, prefixGroups);

                                boolean isOutseized = isOutseizeNumberEnabled && !origin.equalsIgnoreCase(dataRow.getOutseizedOrigin());
                                String routingDecision = null;
                                if (includeRoutingDecision) {
                                    routingDecision = SF.routing().getRoutingDecisionPrefixes(cid).getDecision(dataRow.getRoutingDecisionPrefix());
                                    try {
                                        Integer crid = null;
                                        if (routingDecision.equals(RoutingDecisionPrefixes.DEFAULT)) {
                                            crid = dataRow.getCrid();
                                        } else {
                                            crid = Integer.parseInt(routingDecision);
                                        }
                                        if (crid != null) {
                                            routingDecision = routeMap.get(crid).getTitle();
                                        }
                                    } catch (Exception e) {
                                    }
                                }

                                if (hasFilters) {
                                    if (filterAggregateData(filters, dataRow, carriers, unknownCarrier, routeMap, routingManagers, originCountry, destinationCountry,
                                            originRegion, destinationRegion, routingManagersCountriesMap, snp, matchingDestinations, matchingOrigins, inFilterCcids,
                                            outFilterCcids, accMgrMap, prefixGroups, routingDecision, dstPgItem, orgPgItem)) {
                                        continue;
                                    }
                                }

                                Time inTimeBand = dataRow.getInTime();
                                Time outTimeBand = dataRow.getOutTime();
                                String inTrunk = dataRow.getInTrunk();
                                String outTrunk = dataRow.getOutTrunk();

                                double inExchangeRate = 1;
                                double outExchangeRate = 1;
                                try {
                                    inExchangeRate = exchangeRatesMap.get(dataRow.getInCurrency());
                                    outExchangeRate = exchangeRatesMap.get(dataRow.getOutCurrency());
                                } catch (Exception e) {
                                }
                                double inTotal = dataRow.getInTotal();
                                double inOprTotal = dataRow.getInOperationTotal();
                                if (inExchangeRate != 1) {
                                    inTotal = inExchangeRate * inTotal;
                                    inOprTotal = inExchangeRate * inOprTotal;
                                }
                                double outTotal = dataRow.getOutTotal();
                                double outOprTotal = dataRow.getOutOperationalTotal();
                                if (outExchangeRate != 1) {
                                    outTotal = outExchangeRate * dataRow.getOutTotal();
                                    outOprTotal = outExchangeRate * dataRow.getOutOperationalTotal();
                                }

                                String inDeal = null;
                                String outDeal = null;
                                if (includeInDeal) {
                                    if (dataRow.isInBilateral()) {
                                        inDeal = SF.i18n(cid).bilateral();
                                    } else if (dataRow.isInSwap()) {
                                        inDeal = SF.i18n(cid).swap();
                                    }
                                }
                                if (includeOutDeal) {
                                    if (dataRow.isOutBilateral()) {
                                        outDeal = SF.i18n(cid).bilateral();
                                    } else if (dataRow.isOutSwap()) {
                                        outDeal = SF.i18n(cid).swap();
                                    }
                                }

                                if (isSplit) {
                                    String carrierFilter = reportPreset.getFilters().get(ReportPresetFilterType.CARRIER);
                                    String accountManagerFilter = reportPreset.getFilters().get(ReportPresetFilterType.ACCOUNT_MANAGER);
                                    TrafficDirection directionFilter = TrafficDirection.get(reportPreset.getFilters().get(ReportPresetFilterType.TRAFFIC_DIRECTION));
                                    String chargeTypeFilter = reportPreset.getFilters().get(ReportPresetFilterType.CHARGE_TYPE);

                                    for (TrafficDirection td : TrafficDirection.values()) {
                                        if (directionFilter != null && directionFilter != td) {
                                            continue;
                                        }

                                        String carrierName = td == TrafficDirection.INCOMING ? inCarrierName : outCarrierName;
                                        String carrierGroup = td == TrafficDirection.INCOMING ? inCarrierGroupName : outCarrierGroupName;
                                        if (!StringUtil.isBlank(carrierFilter)) {
                                            HashSet<String> carrierNames = new HashSet<>();
                                            carrierNames.add(carrierName);
                                            carrierNames.add(carrierGroup);
                                            if (!checkFilter(carrierFilter, carrierNames)) {
                                                continue;
                                            }
                                        }

                                        String accountManagerName = td == TrafficDirection.INCOMING ? inAccMgr : outAccMgr;
                                        if (!StringUtil.isBlank(accountManagerFilter)) {
                                            if (!checkFilter(accountManagerFilter, accountManagerName)) {
                                                continue;
                                            }
                                        }

                                        String carrierGroupKey = carrierGroup + "-" + td.getId();
                                        String chargeType = null;
                                        SafeDate effectiveFrom = null;
                                        SafeDate effectiveTo = null;

                                        if (td == TrafficDirection.INCOMING) {
                                            chargeType = dataRow.isReverseCharged() ? "COST" : "REVENUE";
                                            DateTime inRateEffectiveFrom = dataRow.getInRateEffectiveFrom();
                                            if (inRateEffectiveFrom != null) {
                                                effectiveFrom = new SafeDate(inRateEffectiveFrom);
                                            }
                                            DateTime inRateEffectiveTo = dataRow.getInRateEffectiveTo();
                                            if (inRateEffectiveTo != null) {
                                                effectiveTo = new SafeDate(inRateEffectiveTo);
                                            }

                                        } else if (td == TrafficDirection.OUTGOING) {
                                            chargeType = !dataRow.isReverseCharged() ? "COST" : "REVENUE";
                                            DateTime outRateEffectiveFrom = dataRow.getOutRateEffectiveFrom();
                                            if (outRateEffectiveFrom != null) {
                                                effectiveFrom = new SafeDate(outRateEffectiveFrom);
                                            }
                                            DateTime outRateEffectiveTo = dataRow.getOutRateEffectiveTo();
                                            if (outRateEffectiveTo != null) {
                                                effectiveTo = new SafeDate(outRateEffectiveTo);
                                            }
                                        }

                                        // TODO-Emre replace equals ignore
                                        if (chargeTypeFilter != null && !chargeTypeFilter.equalsIgnoreCase(chargeType)) {
                                            continue;
                                        }

                                        String key = reportPreset.getAggregatedDataRowKey(dataRow, inCarrierName, outCarrierName, carrierName, inCarrierGroupName,
                                                outCarrierGroupName, carrierGroupKey, originCountry, destinationCountry, originCategory, destinationCategory,
                                                routingClassName, chargeType, td, accountManagerName, inCarrier != null ? inCarrier.getAccountManager() : null,
                                                outCarrier != null ? outCarrier.getAccountManager() : null, countryRoutingManagerId, originRegion, destinationRegion,
                                                snp.getSwitchName(inTrunk), snp.getSwitchName(outTrunk), routingDecision);

                                        AggregatedDataGridRow gridRow = rowMap.get(key);

                                        if (gridRow == null) {
                                            synchronized (key.intern()) {
                                                gridRow = rowMap.get(key);
                                                if (gridRow == null) {
                                                    if (lastTimeCorrected.get() && jValue + 1 == trendRanges.size()) {
                                                        range.setTo(originalTo);
                                                    }

                                                    gridRow = new AggregatedDataGridRow(dataRow.getInCcid(), dataRow.getOutCcid(),
                                                            inCarrierGroup == null ? null : inCarrierGroup.getCcgid(),
                                                            outCarrierGroup == null ? null : outCarrierGroup.getCcgid(), inCarrierName, outCarrierName, carrierName,
                                                            inCarrierGroupName, outCarrierGroupName, carrierGroup, destination, origin, dataRow.getOutseizedOrigin(),
                                                            inTrunk, outTrunk, snp.getSwitchName(inTrunk), snp.getSwitchName(outTrunk), originCountry, destinationCountry,
                                                            originCategory, destinationCategory, dataRow.getInOrigin(), dataRow.getInDestination(),
                                                            dataRow.getOutOrigin(), dataRow.getOutDestination(), inAccMgr, outAccMgr, accountManagerName,
                                                            routingClassName, countryRoutingManagerName, range.getFrom().toDateTime().getMonthOfYear(),
                                                            range.getFrom().toDateTime().getDayOfWeek(), range.getFrom().toDateTime().getHourOfDay(),
                                                            range.getFrom().toDateTime().getWeekOfWeekyear(), range.getFrom().toDateTime().getYear(),
                                                            dataRow.isReverseCharged(), isOutseized, dataRow.getInRate(), dataRow.getOutRate(), inDeal, outDeal,
                                                            dataRow.getInCurrency(), dataRow.getOutCurrency(), effectiveFrom, effectiveTo, td, dataRow.getTrafficFormat(),
                                                            chargeType, originRegion, destinationRegion, inTimeBand, outTimeBand, routingDecision,
                                                            dataRow.getInOperationalRateWithExchangeRate(inExchangeRate),
                                                            dataRow.getOutOperationalRateWithExchangeRate(outExchangeRate), range, sortOrder);

                                                    rowMap.put(key, gridRow);
                                                }

                                            }
                                        }
                                        gridRow.addAggregatedDataRow(dataRow, td, inTotal, outTotal, inOprTotal, outOprTotal, hasOutSorting, includeStatusCodes);
                                    }
                                } else {
                                    String key = reportPreset.getAggregatedDataRowKey(dataRow, inCarrierName, outCarrierName, null, inCarrierGroupName,
                                            outCarrierGroupName, null, originCountry, destinationCountry, originCategory, destinationCategory, routingClassName, null,
                                            null, null, inCarrier != null ? inCarrier.getAccountManager() : null,
                                            outCarrier != null ? outCarrier.getAccountManager() : null, countryRoutingManagerId, originRegion, destinationRegion,
                                            snp.getSwitchName(inTrunk), snp.getSwitchName(outTrunk), routingDecision);
                                    AggregatedDataGridRow gridRow = rowMap.get(key);

                                    if (gridRow == null) {
                                        synchronized (key.intern()) {
                                            gridRow = rowMap.get(key);
                                            if (gridRow == null) {
                                                if (lastTimeCorrected.get() && jValue + 1 == trendRanges.size()) {
                                                    range.setTo(originalTo);
                                                }

                                                gridRow = new AggregatedDataGridRow(dataRow.getInCcid(), dataRow.getOutCcid(),
                                                        inCarrierGroup == null ? null : inCarrierGroup.getCcgid(),
                                                        outCarrierGroup == null ? null : outCarrierGroup.getCcgid(), inCarrierName, outCarrierName, null,
                                                        inCarrierGroupName, outCarrierGroupName, null, destination, origin, dataRow.getOutseizedOrigin(), inTrunk,
                                                        outTrunk, snp.getSwitchName(inTrunk), snp.getSwitchName(outTrunk), originCountry, destinationCountry,
                                                        originCategory, destinationCategory, dataRow.getInOrigin(), dataRow.getInDestination(), dataRow.getOutOrigin(),
                                                        dataRow.getOutDestination(), inAccMgr, outAccMgr, null, routingClassName, countryRoutingManagerName,
                                                        range.getFrom().toDateTime().getMonthOfYear(), range.getFrom().toDateTime().getDayOfWeek(),
                                                        range.getFrom().toDateTime().getHourOfDay(), range.getFrom().toDateTime().getWeekOfWeekyear(),
                                                        range.getFrom().toDateTime().getYear(), dataRow.isReverseCharged(), isOutseized, dataRow.getInRate(),
                                                        dataRow.getOutRate(), inDeal, outDeal, dataRow.getInCurrency(), dataRow.getOutCurrency(), null, null, null,
                                                        dataRow.getTrafficFormat(), null, originRegion, destinationRegion, inTimeBand, outTimeBand, routingDecision,
                                                        dataRow.getInOperationalRateWithExchangeRate(inExchangeRate),
                                                        dataRow.getOutOperationalRateWithExchangeRate(outExchangeRate), range, sortOrder);
                                                rowMap.put(key, gridRow);
                                            }
                                        }
                                    }
                                    gridRow.addAggregatedDataRow(dataRow, inTotal, outTotal, inOprTotal, outOprTotal, hasOutSorting, includeStatusCodes);
                                }
                            }
                        }
                    }));
                }

                boolean isErrorLogged = false;
                for (Future<?> future : futures) {
                    try {
                        future.get();
                    } catch (Exception e) {
                        SF.l(getClass(), cid, e);
                        if (!isErrorLogged) {
                            isErrorLogged = false;
                            debugLog.add(this.getClass(), cid, e);
                        }
                    }
                }

                // Filter kpi values
                HashSet<String> removalKeys = new HashSet<>();
                for (String key : rowMap.keySet()) {
                    AggregatedDataGridRow dataGridRow = rowMap.get(key);
                    boolean filterDataGridRow = filterDataGridRow(reportPreset, dataGridRow);
                    if (filterDataGridRow) {
                        removalKeys.add(key);
                    } else {
                        uniqueKeys.put(key, dataGridRow);
                    }
                }

                for (String key : removalKeys) {
                    rowMap.remove(key);
                }
                allRows.put(range.getFrom(), rowMap);
            }
        }

        if (hasQualityData) {
            Object qualityDataTimer = Chrono.start();

            for (Entry<SafeDate, ConcurrentHashMap<String, AggregatedDataGridRow>> rowEntry : allRows.entrySet()) {
                SafeDate trendDate = rowEntry.getKey();
                QualityData qualityData = qualityDataMap.get(trendDate);
                if (qualityData == null) {
                    continue;
                }

                for (AggregatedDataGridRow dataGridRow : rowEntry.getValue().values()) {
                    HashMap<AggregatedDataType, String> qualityFilters = getQualityFiltersForRow(reportPreset, dataGridRow);
                    QualityDataRow qualityDataRow = qualityData.getData(qualityFilters);
                    dataGridRow.setQualityDataRow(qualityDataRow);
                }
            }
            SF.l(getClass(), cid, "Quality data matching in " + Chrono.stop(qualityDataTimer) + " ms");

        }

        executor.shutdown();
        cancelledReportKeys.remove(cancelKey);
        return allRows;

    }

    private HashMap<AggregatedDataType, String> getQualityFiltersForRow(final ReportPreset reportPreset, final AggregatedDataGridRow dataGridRow) {
        HashMap<AggregatedDataType, String> qualityFilters = new HashMap<>();
        for (AggregateDataColumn col : reportPreset.getColumnList()) {
            AggregatedDataType dataType = col.getColumnType();
            if (dataType == null) {
                continue;
            }

            if (dataType == AggregatedDataType.INCOMING_CARRIER) {
                qualityFilters.put(dataType, String.valueOf(dataGridRow.getInCcid()));
            } else if (dataType == AggregatedDataType.OUTGOING_CARRIER) {
                qualityFilters.put(dataType, String.valueOf(dataGridRow.getOutCcid()));
            } else if (dataType == AggregatedDataType.DESTINATION) {
                qualityFilters.put(dataType, dataGridRow.getDestination());
            } else if (dataType == AggregatedDataType.ORIGIN) {
                qualityFilters.put(dataType, dataGridRow.getOrigin());
            } else if (dataType == AggregatedDataType.ROUTING_CLASS) {
                qualityFilters.put(dataType, dataGridRow.getRoutingClass());
            }
        }
        return qualityFilters;
    }

    public PartialList<ArrayList<AggregatedDataGridRow>> report(int cid, SafeDate from, SafeDate to, ReportPreset reportPreset, Range pagingRange, Order[] order, int uid,
                                                                String ip, boolean removeZeroOrNullRows) {

        long totalTime = 0, removalTime = 0, sortTime = 0;
        Object totalChrono = Chrono.start();

        String displayOptionStr = reportPreset.getFilters().get(ReportPresetFilterType.DISPLAY_OPTION);
        TrendDisplayOptionType displayOption = TrendDisplayOptionType.get(displayOptionStr);
        boolean isHorizontal = displayOption == TrendDisplayOptionType.HORIZONTAL;

        LinkedList<AggregateDataColumn> columns = reportPreset.getColumnList();
        if (order == null) {
            order = new Order[columns.size()];
            int i = 0;
            for (AggregateDataColumn column : columns) {
                Order o = new Order(column.getTypeName(), true);
                order[i++] = o;
            }
        }
        if (isHorizontal) {
            // In horizontal reports only order text columns.

            ArrayList<Order> filteredOrder = new ArrayList<>();
            for (Order o : order) {
                AggregatedDataType adt;
                try {
                    adt = AggregatedDataType.valueOf(o.getOrderBy());
                    if (adt == AggregatedDataType.DATE || adt == AggregatedDataType.HOUR_OF_DAY || adt == AggregatedDataType.DAY_OF_WEEK
                            || adt == AggregatedDataType.WEEK_OF_YEAR || adt == AggregatedDataType.MONTH || adt == AggregatedDataType.YEAR) {
                        continue;
                    } else if (!adt.isText()) {
                        continue;
                    }
                } catch (IllegalArgumentException e) {
                    // This order is not a valid AggregatedDataType.
                    // Considering we only eliminate some AggregateDataTypes during ordering; this can be a QualityStoreDataType
                    // which is allowed for order.
                }

                filteredOrder.add(o);
            }
            order = filteredOrder.toArray(new Order[0]);

        }
        final Order[] sortOrder = order;

        String gridKey = reportPreset.getKey() + "-" + from + "-" + to;
        String dashboardWidgetGridKey = reportPreset.getKeyWithoutId() + "-" + from + "-" + to;
        LinkedHashMap<SafeDate, ConcurrentHashMap<String, AggregatedDataGridRow>> allRows = reportPreset.isFromDashboardWidget()
                ? dashboardWidgetcache.getIfPresent(dashboardWidgetGridKey)
                : cache.getIfPresent(gridKey);
        if (!SF.d().isTrafficAndProfitabilityCacheEnabled(cid) && !reportPreset.isFromDashboardWidget()) {
            allRows = null;
            SF.l(this.getClass(), cid, "Cache not enabled. Calculating results.");
        }
        if (allRows == null) {
            synchronized (getCacheLock(gridKey)) {
                allRows = reportPreset.isFromDashboardWidget() ? dashboardWidgetcache.getIfPresent(dashboardWidgetGridKey) : cache.getIfPresent(gridKey);
                if (!SF.d().isTrafficAndProfitabilityCacheEnabled(cid) && !reportPreset.isFromDashboardWidget()) {
                    allRows = null;
                }
                if (allRows == null) {
                    allRows = getRows(cid, from, to, reportPreset, pagingRange, sortOrder, uid, ip);
                    if (reportPreset.isFromDashboardWidget()) {
                        dashboardWidgetcache.put(dashboardWidgetGridKey, allRows);
                    } else {
                        cache.put(gridKey, allRows);
                    }
                }
            }
        }

        Object sortChrono = Chrono.start();

        LinkedList<ArrayList<AggregatedDataGridRow>> list = new LinkedList<>();
        if (isHorizontal) {
            LinkedHashMap<SafeDate, HashMap<String, AggregatedDataGridRow>> allCopy = new LinkedHashMap<>();

            HashMap<String, AggregatedDataGridRow> keysToRows = new HashMap<>();
            for (SafeDate sd : allRows.keySet()) {
                for (String key : allRows.get(sd).keySet()) {
                    keysToRows.put(key, allRows.get(sd).get(key));
                }
            }

            for (SafeDate sd : allRows.keySet()) {
                if (allRows.get(sd).values() != null && allRows.get(sd).values().size() > 0) {
                    DateRange range = ((AggregatedDataGridRow) allRows.get(sd).values().toArray()[0]).getDateRange();
                    for (String key : keysToRows.keySet()) {
                        AggregatedDataGridRow rowToAdd = null;
                        if (!allRows.get(sd).containsKey(key)) {
                            rowToAdd = keysToRows.get(key).clone();
                            rowToAdd.setDateRange(range);
                            rowToAdd.clearValues();
                        } else {
                            rowToAdd = allRows.get(sd).get(key).clone();
                        }

                        HashMap<String, AggregatedDataGridRow> rowMap = allCopy.get(sd);
                        if (rowMap == null) {
                            rowMap = new HashMap<>();
                            allCopy.put(sd, rowMap);
                        }

                        rowMap.put(key, rowToAdd);
                    }
                }
            }

            for (HashMap<String, AggregatedDataGridRow> map : allCopy.values()) {
                ArrayList<AggregatedDataGridRow> consolidatedRows = new ArrayList<>();
                for (AggregatedDataGridRow row : map.values()) {
                    consolidatedRows.add(row);
                }
                if (consolidatedRows.size() == 0) {
                    continue;
                }

                Collections.sort(consolidatedRows, new Comparator<AggregatedDataGridRow>() {

                    @Override
                    public int compare(AggregatedDataGridRow r1, AggregatedDataGridRow r2) {
                        if (r1 == null || r2 == null) {
                            return 0;
                        }
                        for (Order o : sortOrder) {
                            int result = r1.compareTo(r2, o);
                            if (result != 0) {
                                return result;
                            }
                        }

                        return 0;
                    }
                });
                list.add(consolidatedRows);
            }

            // transpose list
            int listSize = list.size();
            LinkedList<ArrayList<AggregatedDataGridRow>> finale = new LinkedList<>();
            if (listSize > 0) {
                int transposeSize = list.get(0).size();
                for (int i = 0; i < transposeSize; i++) {
                    ArrayList<AggregatedDataGridRow> transposed = new ArrayList<>();
                    for (ArrayList<AggregatedDataGridRow> trendList : list) {
                        transposed.add(trendList.get(i));
                    }
                    finale.add(transposed);
                }

                Object removalChrono = Chrono.start();
                if (removeZeroOrNullRows) {
                    removeZeroOrNullDataRows(reportPreset, finale);
                }
                removalTime = Chrono.stop(removalChrono);
                list = finale;
            }

        } else {
            ArrayList<AggregatedDataGridRow> allRowsList = new ArrayList<>();
            for (Entry<SafeDate, ConcurrentHashMap<String, AggregatedDataGridRow>> entry2 : allRows.entrySet()) {
                if (entry2.getValue().keySet().size() == 0) {
                    AggregatedDataGridRow gridRow = new AggregatedDataGridRow();
                    gridRow.setDateRange(new DateRange(entry2.getKey(), entry2.getKey()));
                    allRowsList.add(gridRow);
                } else {
                    for (Entry<String, AggregatedDataGridRow> entry3 : entry2.getValue().entrySet()) {
                        allRowsList.add(entry3.getValue());
                    }
                }
            }
            Collections.sort(allRowsList, new Comparator<AggregatedDataGridRow>() {

                @Override
                public int compare(AggregatedDataGridRow r1, AggregatedDataGridRow r2) {
                    if (r1 == null || r2 == null) {
                        return 0;
                    }
                    for (Order o : sortOrder) {
                        int result = r1.compareTo(r2, o);
                        if (result != 0) {
                            return result;
                        }
                    }

                    return 0;
                }
            });

            for (

                    int i = 0; i < allRowsList.size(); i++) {

                ArrayList<AggregatedDataGridRow> transposed = new ArrayList<>();

                AggregatedDataGridRow adgr = allRowsList.get(i);
                transposed.add(adgr);
                list.add(transposed);
            }

            Object removalChrono = Chrono.start();

            if (removeZeroOrNullRows) {

                removeZeroOrNullDataRows(reportPreset, list);
            }
            removalTime = Chrono.stop(removalChrono);
        }
        sortTime = Chrono.stop(sortChrono);

        int start;

        int totalSize = list.size();
        // PagingRange should only be null on export.
        if (pagingRange == null) {
            start = 0;
            pagingRange = new Range(start, totalSize);
        } else {
            start = pagingRange.getStart();
        }

        boolean includeStatusCodes = reportPreset.hasColumn(AggregatedDataType.STATUS_CODES)
                || reportPreset.getFilters().containsKey(ReportPresetFilterType.STATUS_CODES);

        PartialList<ArrayList<AggregatedDataGridRow>> result = new PartialList<>();
        result.setTotalSize(totalSize);

        // Calculate total row of all data and pass it to partial list
        ArrayList<AggregatedDataGridRow> totals = new ArrayList<>();
        if (list.size() > 0) {
            if (isHorizontal) {
                int totalsCount = list.get(0).size();
                for (int i = 0; i < totalsCount; i++) {
                    AggregatedDataGridRow totalRow = new AggregatedDataGridRow();
                    for (ArrayList<AggregatedDataGridRow> dataRows : list) {
                        totalRow.addAggregatedDataGridRow(dataRows.get(i), includeStatusCodes);
                    }
                    totals.add(totalRow);
                }
            } else {
                AggregatedDataGridRow totalRow = new AggregatedDataGridRow();
                for (ArrayList<AggregatedDataGridRow> dataRows : list) {
                    for (AggregatedDataGridRow dataRow : dataRows) {
                        totalRow.addAggregatedDataGridRow(dataRow, includeStatusCodes);
                    }
                }
                totals.add(totalRow);
            }
        }
        result.setTotalRow(totals);

        int toIndex = pagingRange.getStart() + pagingRange.getLength();
        if (toIndex >= totalSize) {
            toIndex = totalSize;
        }

        ArrayList<ArrayList<AggregatedDataGridRow>> finalList = (ArrayList<ArrayList<AggregatedDataGridRow>>) list.subList(pagingRange.getStart(), toIndex).stream()
                .collect(Collectors.toList());
        result.setList(finalList);
        result.setRange(pagingRange);

        totalTime = Chrono.stop(totalChrono);
        SF.l(getClass(), cid, "From: " + from.toDateTime().toString("yyyy-MM-dd HH:mm") + ", to: " + to.toDateTime().toString("yyyy-MM-dd HH:mm") + ", Sort: " + sortTime
                + "ms, Removal: " + removalTime + "ms, TOTAL: " + totalTime + "ms. " + reportPreset.toStringTFLog());
        return result;
    }

    private void removeZeroOrNullDataRows(ReportPreset reportPreset, LinkedList<ArrayList<AggregatedDataGridRow>> result) {
        ArrayList<AggregateDataColumn> nonTextCols = new ArrayList<>();
        for (AggregateDataColumn column : reportPreset.getColumnList()) {
            AggregatedDataType columnType = column.getColumnType();
            QualityStoreDataType qualityDataType = column.getQualityStoreDataType();
            if (columnType != null && !columnType.isText()) {
                nonTextCols.add(column);
            } else if (qualityDataType != null && !qualityDataType.isString()) {
                nonTextCols.add(column);
            }
        }
        for (Iterator<ArrayList<AggregatedDataGridRow>> listIterator = result.iterator(); listIterator.hasNext();) {
            ArrayList<AggregatedDataGridRow> rowList = listIterator.next();
            int removedTrendCount = 0;
            for (Iterator<AggregatedDataGridRow> rowIterator = rowList.iterator(); rowIterator.hasNext();) {
                AggregatedDataGridRow row = rowIterator.next();
                boolean skipRow = true;

                for (AggregateDataColumn col : nonTextCols) {
                    AggregatedDataType columnType = col.getColumnType();
                    QualityStoreDataType qualityDataType = col.getQualityStoreDataType();
                    Object data;
                    if (columnType != null) {
                        data = row.getData(columnType);
                    } else {
                        data = row.getQualityData(qualityDataType);
                    }
                    if (data != null) {
                        try {
                            Double numericData = new Double(data.toString());
                            skipRow &= numericData == 0d;
                        } catch (Exception e) {
                            skipRow &= false;
                        }
                    }

                }

                if (skipRow) {
                    removedTrendCount++;
                }
            }
            if (rowList.size() == removedTrendCount) {
                listIterator.remove();
            }
        }
    }

    private ArrayList<DateRange> getExchangeRanges(DateRange rangeToDivide, ProfitabilityExchangeRateType type) {
        ArrayList<DateRange> ranges = new ArrayList<>();
        if (!(type == ProfitabilityExchangeRateType.DAILY || type == ProfitabilityExchangeRateType.MONTHLY)) {
            ranges.add(rangeToDivide);
            return ranges;
        }
        DateTime fromDt = rangeToDivide.getFrom().toDateTime();
        while (true) {
            DateTime toDt = null;
            if (type == ProfitabilityExchangeRateType.DAILY) {
                toDt = fromDt.plusHours(24);
            } else {
                toDt = fromDt.withDayOfMonth(1).plusMonths(1);
            }

            if (toDt.isBefore(rangeToDivide.getTo().toDateTime().getMillis())) {
                DateRange trendRange = new DateRange(new SafeDate(fromDt), new SafeDate(toDt));
                ranges.add(trendRange);
                fromDt = toDt;
            } else {
                DateRange trendRange = new DateRange(new SafeDate(fromDt), rangeToDivide.getTo());
                ranges.add(trendRange);
                break;
            }
        }

        return ranges;
    }

    private ArrayList<DateRange> getRanges(DateTime from, DateTime to, ReportPreset reportPreset) {
        HashSet<DateRange> ranges = new HashSet<>();
        boolean isTrendReport = reportPreset.getFilters().containsKey(ReportPresetFilterType.PERIOD);
        if (isTrendReport) {
            String period = reportPreset.getFilters().get(ReportPresetFilterType.PERIOD);
            TrafficTrendsResolution resolution = TrafficTrendsResolution.get(Integer.parseInt(period));
            while (from.isBefore(to)) {

                DateTime drFrom = from;
                int offset = 0;
                boolean noMinuteDifferenceRequired = false;
                switch (resolution) {
                    case DAILY:
                        offset = 24 * 60;
                        from = from.plusDays(1);
                        break;
                    case HOURS_12:
                        offset = 12 * 60;
                        from = from.plusHours(12);
                        break;
                    case HOURS_3:
                        offset = 3 * 60;
                        from = from.plusHours(3);
                        break;
                    case HOURS_6:
                        offset = 6 * 60;
                        from = from.plusHours(6);
                        break;
                    case MONTHLY:
                        noMinuteDifferenceRequired = true;
                        from = from.plusMonths(1);
                        break;
                    case MINUTES_15:
                        offset = 15;
                        from = from.plusMinutes(15);
                        break;
                    case MINUTES_5:
                        offset = 5;
                        from = from.plusMinutes(5);
                        break;
                    case WEEKLY:
                        noMinuteDifferenceRequired = true;
                        from = from.plusWeeks(1);
                        break;
                    case HOURLY:
                    default:
                        offset = 60;
                        from = from.plusHours(1);
                        break;
                }

                SafeDate from1 = new SafeDate(drFrom);
                SafeDate from2 = new SafeDate(from);

                int diff = from2.getMinuteOfDay() - from1.getMinuteOfDay();
                if (!noMinuteDifferenceRequired) {
                    if (from.withTimeAtStartOfDay().isAfter(drFrom.withTimeAtStartOfDay())) {
                        diff += 24 * 60;
                    }
                    if (diff < offset) {
                        from = from.plusHours(1);
                        from2 = new SafeDate(from);
                    } else if (diff > offset && (offset > 60)) {
                        from = from.minusHours(1);
                        from2 = new SafeDate(from);
                    }
                }

                DateRange range = new DateRange(from1, from2);
                ranges.add(range);
            }
        } else {
            DateRange range = new DateRange(from, to);
            ranges.add(range);
        }

        ArrayList<DateRange> rangeList = new ArrayList<>(ranges);
        Collections.sort(rangeList, new Comparator<DateRange>() {

            @Override
            public int compare(DateRange o1, DateRange o2) {
                return o1.getFrom().compareTo(o2.getFrom());
            }
        });
        return rangeList;

    }

    private DestinationCategory getCategory(PgItem pgItem, PrefixGroups prefixGroups) {
        if (pgItem != null) {
            return pgItem.getDestinationCategory();
        }

        return null;
    }

    private DestinationCategory getCategory(String destination, PrefixGroups prefixGroups) {
        return getCategory(prefixGroups.get(destination), prefixGroups);
    }

    private boolean filterAggregateData(HashMap<ReportPresetFilterType, String> filters, AggregatedDataRow dataRow, LinkedHashMap<Integer, Carrier> carriers,
                                        Carrier unknownCarrier, HashMap<Integer, Route> routeMap, LinkedHashMap<Integer, User> routingManagers, String originCountry, String destinationCountry,
                                        String originRegion, String destinationRegion, LinkedHashMap<Integer, HashSet<Country>> routingManagersCountriesMap, SwitchNameProvider switchNameProvider,
                                        HashSet<String> destinations, HashSet<String> origins, HashSet<Integer> inFilterCcids, HashSet<Integer> outFilterCcids, HashMap<Integer, String> accMgrMap,
                                        PrefixGroups prefixGroups, String routingDecision, PgItem dstPgItem, PgItem orgPgItem) {

        for (Entry<ReportPresetFilterType, String> filterEntry : filters.entrySet()) {
            boolean filter = true;
            String filterValue = filterEntry.getValue();
            ReportPresetFilterType key = filterEntry.getKey();
            if (key == ReportPresetFilterType.ACCOUNT_MANAGER) {
                if (accMgrMap != null && accMgrMap.size() > 0) {
                    String inAccountManagerName = accMgrMap.get(dataRow.getInCcid());
                    String outAccountManagerName = accMgrMap.get(dataRow.getOutCcid());
                    if ((inAccountManagerName != null && !inAccountManagerName.isEmpty() && checkFilter(filterValue, inAccountManagerName))
                            || (outAccountManagerName != null && !outAccountManagerName.isEmpty() && checkFilter(filterValue, outAccountManagerName))) {
                        filter = false;
                    }
                }
            } else if (key == ReportPresetFilterType.DESTINATION) {
                if (destinations.contains(dataRow.getDestination())) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.DESTINATION_CATEGORY) {
                DestinationCategory category = getCategory(dstPgItem, prefixGroups);
                if (category != null) {

                    String destinationCategory = category.getId();

                    String[] splitted = filterValue.split(",");

                    if (splitted.length > 1) {
                        HashSet<String> filterSet = new HashSet<>();
                        for (int i = 0; i < splitted.length; i++) {
                            filterSet.add(splitted[i].trim());
                        }
                        if (checkFilter(destinationCategory, filterSet)) {
                            filter = false;
                        }
                    } else {
                        if (checkFilter(filterValue, destinationCategory)) {
                            filter = false;
                        }
                    }
                }

            } else if (key == ReportPresetFilterType.DESTINATION_COUNTRY) {
                if ((!StringUtil.isBlank(destinationCountry) && checkFilter(filterValue, destinationCountry))
                        || (!StringUtil.isBlank(destinationRegion) && checkFilter(filterValue, destinationRegion))) {
                    filter = false;
                }

            } else if (key == ReportPresetFilterType.INCOMING_ACCOUNT_MANAGER) {
                if (accMgrMap != null && accMgrMap.size() > 0) {
                    String accountManagerName = accMgrMap.get(dataRow.getInCcid());
                    if (accountManagerName != null && !accountManagerName.isEmpty() && checkFilter(filterValue, accountManagerName)) {
                        filter = false;
                    }
                }
            } else if (key == ReportPresetFilterType.INCOMING_BILATERAL_DEAL) {
                if (filterValue != null) {
                    if (TargetRouteType.BILATERAL.name().equalsIgnoreCase(filterValue)) {
                        if (dataRow.isInBilateral()) {
                            filter = false;
                        }
                    } else if (TargetRouteType.SWAP.name().equalsIgnoreCase(filterValue)) {
                        if (dataRow.isInSwap()) {
                            filter = false;
                        }
                    } else {
                        if (!dataRow.isInBilateral() && !dataRow.isInSwap()) {
                            filter = false;
                        }
                    }
                }
            } else if (key == ReportPresetFilterType.INCOMING_CARRIER) {
                int ccid = dataRow.getInCcid();
                if (carriers.get(ccid) == null) {
                    ccid = unknownCarrier.getCcid();
                }
                if (inFilterCcids.contains(ccid)) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.INCOMING_CURRENCY) {
                Currency byName = Currency.getByName(filterValue);
                if (dataRow.getInCurrency() == byName) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.INCOMING_RATE) {
                boolean filterRate = Kpi.filter(filterValue, dataRow.getInRate());
                if (!filterRate) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.INCOMING_SWITCH) {
                String swName = switchNameProvider.getSwitchName(dataRow.getInTrunk());
                if (checkFilter(filterValue, swName)) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.INCOMING_TIME_BAND) {
                String[] splitted = filterValue.split(",");
                String inTimeName = dataRow.getInTime() == null ? Time.ALL_DAY.name() : dataRow.getInTime().name();
                if (splitted.length > 1) {
                    HashSet<String> filterSet = new HashSet<>();
                    for (int i = 0; i < splitted.length; i++) {
                        filterSet.add(splitted[i].trim());
                    }
                    if (checkFilter(inTimeName, filterSet)) {
                        filter = false;
                    }
                } else {
                    if (checkFilter(filterValue, inTimeName)) {
                        filter = false;
                    }
                }

            } else if (key == ReportPresetFilterType.INCOMING_TRUNK) {
                if (checkFilter(filterValue, dataRow.getInTrunk())) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.OFFERED_DESTINATION) {
                if (checkFilter(filterValue, dataRow.getInDestination())) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.OFFERED_ORIGIN) {
                if (checkFilter(filterValue, dataRow.getInOrigin())) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.ORIGIN) {
                if (origins.contains(dataRow.getOrigin())) {
                    filter = false;
                }

            } else if (key == ReportPresetFilterType.ORIGIN_CATEGORY) {
                DestinationCategory category = getCategory(orgPgItem, prefixGroups);
                if (category != null) {
                    String originCategory = category.getId();
                    String[] splitted = filterValue.split(",");
                    if (splitted.length > 1) {
                        HashSet<String> filterSet = new HashSet<>();
                        for (int i = 0; i < splitted.length; i++) {
                            filterSet.add(splitted[i].trim());
                        }
                        if (checkFilter(originCategory, filterSet)) {
                            filter = false;
                        }
                    } else {
                        if (checkFilter(filterValue, originCategory)) {
                            filter = false;
                        }
                    }
                }
            } else if (key == ReportPresetFilterType.ORIGIN_COUNTRY) {
                if ((!StringUtil.isBlank(originCountry) && checkFilter(filterValue, originCountry))
                        || (!StringUtil.isBlank(originRegion) && checkFilter(filterValue, originRegion))) {
                    filter = false;
                }

            } else if (key == ReportPresetFilterType.OUTGOING_ACCOUNT_MANAGER) {
                if (accMgrMap != null && accMgrMap.size() > 0) {
                    String accountManagerName = accMgrMap.get(dataRow.getOutCcid());
                    if (accountManagerName != null && !accountManagerName.isEmpty() && checkFilter(filterValue, accountManagerName)) {
                        filter = false;
                    }
                }
            } else if (key == ReportPresetFilterType.OUTGOING_BILATERAL_DEAL) {
                if (filterValue != null) {
                    if (TargetRouteType.BILATERAL.name().equalsIgnoreCase(filterValue)) {
                        if (dataRow.isOutBilateral()) {
                            filter = false;
                        }
                    } else if (TargetRouteType.SWAP.name().equalsIgnoreCase(filterValue)) {
                        if (dataRow.isOutSwap()) {
                            filter = false;
                        }
                    } else {
                        if (!dataRow.isOutBilateral() && !dataRow.isOutSwap()) {
                            filter = false;
                        }
                    }
                }
            } else if (key == ReportPresetFilterType.OUTGOING_CARRIER) {
                int ccid = dataRow.getOutCcid();
                if (carriers.get(ccid) == null) {
                    ccid = unknownCarrier.getCcid();
                }
                if (outFilterCcids.contains(ccid)) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.OUTGOING_CURRENCY) {
                Currency byName = Currency.getByName(filterValue);
                if (dataRow.getOutCurrency() == byName) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.OUTGOING_RATE) {
                boolean filterRate = Kpi.filter(filterValue, dataRow.getOutRate());
                if (!filterRate) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.OUTGOING_TIME_BAND) {
                String[] splitted = filterValue.split(",");
                String outTimeName = dataRow.getOutTime() == null ? Time.ALL_DAY.name() : dataRow.getOutTime().name();
                if (splitted.length > 1) {
                    HashSet<String> filterSet = new HashSet<>();
                    for (int i = 0; i < splitted.length; i++) {
                        filterSet.add(splitted[i].trim());
                    }
                    if (checkFilter(outTimeName, filterSet)) {
                        filter = false;
                    }
                } else {
                    if (checkFilter(filterValue, outTimeName)) {
                        filter = false;
                    }
                }
            } else if (key == ReportPresetFilterType.OUTGOING_TRUNK) {
                if (checkFilter(filterValue, dataRow.getOutTrunk())) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.OUTGOING_SWITCH) {
                String swName = switchNameProvider.getSwitchName(dataRow.getOutTrunk());
                if (checkFilter(filterValue, swName)) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.OUTSIEZED) {
                boolean isOutseized = filterValue.equalsIgnoreCase("true");
                if (isOutseized != (dataRow.getOrigin().equalsIgnoreCase(dataRow.getOutseizedOrigin()))) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.REVERSE_CHARGING) {
                boolean isReverseCharged = filterValue.equalsIgnoreCase("true");
                if (isReverseCharged == dataRow.isReverseCharged()) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.ROUTING_CLASS) {

                for (String crid : filterValue.split(",")) {
                    try {
                        Integer cridInt = Integer.parseInt(crid);
                        if (dataRow.getCrid() == cridInt.intValue()) {
                            filter = false;
                            break;
                        }
                    } catch (Exception e) {
                    }
                }
            } else if (key == ReportPresetFilterType.ROUTING_DECISION) {

                if (filterValue != null) {
                    String[] decisionFilters = filterValue.split(",");
                    for (String decisionFilter : decisionFilters) {
                        Integer crid = null;
                        try {
                            crid = Integer.parseInt(decisionFilter);
                        } catch (Exception e) {
                        }
                        if (crid != null) {
                            try {
                                decisionFilter = routeMap.get(crid.intValue()).getTitle();
                            } catch (Exception e) {
                            }
                        }
                        if (routingDecision != null) {
                            boolean decisionCheck = decisionFilter.trim().equalsIgnoreCase(routingDecision.trim());
                            if (decisionCheck) {
                                filter = false;
                                break;
                            }
                        }
                    }
                }
            } else if (key == ReportPresetFilterType.ROUTING_MANAGER) {
                Integer rmId = null;
                for (Entry<Integer, User> entry : routingManagers.entrySet()) {
                    if (checkFilter(filterValue, entry.getValue().getDisplayName())) {
                        rmId = entry.getKey();
                        break;
                    }
                }
                if (rmId != null) {
                    HashSet<Country> countries = routingManagersCountriesMap.get(rmId);
                    if (countries != null && countries.size() > 0) {
                        Set<String> countryNames = countries.stream().map(country -> country.getEnglishShortName()).collect(Collectors.toSet());
                        if (!StringUtil.isBlank(destinationCountry)) {
                            if (checkFilter(destinationCountry, countryNames)) {
                                filter = false;
                            }
                        }
                    }
                }
            } else if (key == ReportPresetFilterType.TRAFFIC_FORMAT) {
                String[] split = filterValue.split(",");
                if (split.length > 1) {
                    HashSet<TrafficFormat> formats = new HashSet<>();
                    for (int i = 0; i < split.length; i++) {
                        formats.add(TrafficFormat.get(split[i]));
                    }
                    if (formats.contains(dataRow.getTrafficFormat())) {
                        filter = false;
                    }

                } else {
                    TrafficFormat format = TrafficFormat.get(filterValue);
                    if (dataRow.getTrafficFormat() == format) {
                        filter = false;
                    }
                }
            } else if (key == ReportPresetFilterType.SUPPLIER_DESTINATION) {
                if (checkFilter(filterValue, dataRow.getOutDestination())) {
                    filter = false;
                }
            } else if (key == ReportPresetFilterType.SUPPLIER_ORIGIN) {
                if (checkFilter(filterValue, dataRow.getOutOrigin())) {
                    filter = false;
                }
            } else {
                continue;
            }

            if (filter) { // No need to look forward
                return true;
            }
        }
        return false;

    }

    private boolean filterDataGridRow(ReportPreset preset, AggregatedDataGridRow gridRow) {
        boolean skipRow = false;
        HashMap<ReportPresetFilterType, String> filters = preset.getFilters();
        for (Entry<ReportPresetFilterType, String> filterEntry : filters.entrySet()) {
            boolean filter = true;
            String filterValue = filterEntry.getValue();
            ReportPresetFilterType key = filterEntry.getKey();
            if (key == ReportPresetFilterType.ACD) {
                filter = Kpi.filter(filterValue, gridRow.getAcd());
            } else if (key == ReportPresetFilterType.ANSWERED_CALLS) {
                filter = Kpi.filter(filterValue, gridRow.getAnsweredCalls());
            } else if (key == ReportPresetFilterType.ASR) {
                filter = Kpi.filter(filterValue, gridRow.getAsr() * 100.0);
            } else if (key == ReportPresetFilterType.CALL_ATTEMPTS) {
                filter = Kpi.filter(filterValue, gridRow.getCallAttempts());
            } else if (key == ReportPresetFilterType.CLI) {
                filter = Kpi.filter(filterValue, gridRow.getCli() * 100.0);
            } else if (key == ReportPresetFilterType.CPM || key == ReportPresetFilterType.WAC) {
                filter = Kpi.filter(filterValue, gridRow.getCpm());
            } else if (key == ReportPresetFilterType.INCOMING_MINUTES) {
                filter = Kpi.filter(filterValue, gridRow.getIncomingLengthInMinutes());
            } else if (key == ReportPresetFilterType.MINUTES) {
                filter = Kpi.filter(filterValue, gridRow.getLengthInMinutes());
            } else if (key == ReportPresetFilterType.NER) {
                filter = Kpi.filter(filterValue, gridRow.getNer() * 100.0);
            } else if (key == ReportPresetFilterType.OUTGOING_MINUTES) {
                filter = Kpi.filter(filterValue, gridRow.getOutgoingLengthInMinutes());
            } else if (key == ReportPresetFilterType.PDD) {
                filter = Kpi.filter(filterValue, gridRow.getPdd());
            } else if (key == ReportPresetFilterType.PPM) {
                filter = Kpi.filter(filterValue, gridRow.getPpm());
            } else if (key == ReportPresetFilterType.PROFIT) {
                filter = Kpi.filter(filterValue, gridRow.getProfit());
                if (preset.hasColumn(AggregatedDataType.OPERATIONAL_PROFIT)) {
                    filter |= Kpi.filter(filterValue, gridRow.getOperationalProfit());
                }
            } else if (key == ReportPresetFilterType.PROFIT_MARGIN) {
                filter = Kpi.filter(filterValue, gridRow.getProfitMargin() * 100.0);
                if (preset.hasColumn(AggregatedDataType.OPERATIONAL_PROFIT_MARGIN)) {
                    filter |= Kpi.filter(filterValue, gridRow.getOperationalProfitMargin() * 100.0);
                }
            } else if (key == ReportPresetFilterType.RPM) {
                filter = Kpi.filter(filterValue, gridRow.getRpm());
            } else if (key == ReportPresetFilterType.SUCCESSFUL_CALLS) {
                filter = Kpi.filter(filterValue, gridRow.getSuccessfulCalls());
            } else if (key == ReportPresetFilterType.STATUS_CODES) {
                String[] codes = filterValue.split(",");
                boolean applyCodeFilter = true;
                for (String code : codes) {
                    code = code.trim();
                    applyCodeFilter &= gridRow.getStatusCodes().keySet().contains(code.toUpperCase());
                }
                filter = !applyCodeFilter;
            } else if (key == ReportPresetFilterType.TOTAL_COST) {
                filter = Kpi.filter(filterValue, gridRow.getTotalCost());
                if (preset.hasColumn(AggregatedDataType.OPERATIONAL_COST)) {
                    filter |= Kpi.filter(filterValue, gridRow.getOperationalCost());
                }
                if (preset.hasColumn(AggregatedDataType.ORIGINAL_COST)) {
                    filter |= Kpi.filter(filterValue, gridRow.getOriginalCost());
                }
            } else if (key == ReportPresetFilterType.TOTAL_REVENUE) {
                filter = Kpi.filter(filterValue, gridRow.getTotalRevenue());
                if (preset.hasColumn(AggregatedDataType.OPERATIONAL_REVENUE)) {
                    filter |= Kpi.filter(filterValue, gridRow.getOperationalRevenue());
                }
                if (preset.hasColumn(AggregatedDataType.ORIGINAL_REVENUE)) {
                    filter |= Kpi.filter(filterValue, gridRow.getOriginalRevenue());
                }
            } else if (key == ReportPresetFilterType.UNSUCCESSFUL_CALLS) {
                filter = Kpi.filter(filterValue, gridRow.getUnsuccessfulCalls());
            } else {
                continue;
            }
            skipRow |= filter;
        }

        return skipRow;
    }

    public ExportResult exportReport(int cid, SafeDate from, SafeDate to, ReportPreset reportPreset, Order[] order, Integer uid, String ip,
                                     CustomReportExportType exportOption) {
        PartialList<ArrayList<AggregatedDataGridRow>> report = report(cid, from, to, reportPreset, null, order, uid == null ? 0 : uid, ip, true);
        File file = createFileFromReportPreset(cid, from, to, reportPreset, report, exportOption);
        return new ExportResult(SF.file().getDownloadUrl(cid, file), file.getName(), file.getPath(), report.getTotalSize() == 0, exportOption);
    }

    private File createFileFromReportPreset(int cid, SafeDate from, SafeDate to, ReportPreset reportPreset, PartialList<ArrayList<AggregatedDataGridRow>> report,
                                            CustomReportExportType exportOption) {
        LinkedList<AggregateDataColumn> columns = reportPreset.getColumnList();
        ArrayList<ExcelStyleType> types = new ArrayList<>();
        String displayOptionStr = reportPreset.getFilters().get(ReportPresetFilterType.DISPLAY_OPTION);
        TrendDisplayOptionType displayOption = TrendDisplayOptionType.get(displayOptionStr);
        boolean isHorizontal = displayOption == TrendDisplayOptionType.HORIZONTAL;
        String period = reportPreset.getFilters().get(ReportPresetFilterType.PERIOD);
        Integer resolutionId = null;
        try {
            resolutionId = Integer.parseInt(period);
        } catch (Exception e) {
        }
        TrafficTrendsResolution resolution = TrafficTrendsResolution.get(resolutionId);
        DateTimeFormatter dateTimeFormatter = getDateTimeFormatter(resolution);
        Table table = null;

        boolean includeStatusCodes = reportPreset.hasColumn(AggregatedDataType.STATUS_CODES)
                || reportPreset.getFilters().containsKey(ReportPresetFilterType.STATUS_CODES);

        if (isHorizontal) {
            SimpleEntry<Table, ArrayList<ExcelStyleType>> tableInfo = createHorizontalTable(cid, reportPreset, report, resolution);
            table = tableInfo.getKey();
            types = tableInfo.getValue();
        } else {

            if (report.getList() != null && report.getList().size() > 0) {
                table = new Table();
                String[] columnNames = columns.stream().map(AggregateDataColumn::getName).toArray(size -> new String[size]);
                table.addColumn(columnNames);

                AggregatedDataGridRow totalRow = new AggregatedDataGridRow();
                HashSet<AggregatedDataType> dataTypesForFooter = getDataTypesForFooter();

                for (ArrayList<AggregatedDataGridRow> dataRows : report.getList()) {
                    for (AggregatedDataGridRow dataRow : dataRows) {
                        totalRow.addAggregatedDataGridRow(dataRow, includeStatusCodes);
                        int r = table.addRow();
                        int c = 0;
                        for (AggregateDataColumn column : columns) {
                            AggregatedDataType dataType = column.getColumnType();
                            if (dataType != null) {
                                Object data = dataRow.getData(dataType);
                                String strVal = formatDataForType(dataType, data, dateTimeFormatter);
                                table.set(r, c++, strVal);
                            } else {
                                QualityStoreDataType type = column.getQualityStoreDataType();
                                Object data = dataRow.getQualityData(type);
                                String strVal = formatDataForType(type, data, dateTimeFormatter);
                                table.set(r, c++, strVal);
                            }

                        }
                    }
                }
                int r = table.addRow();
                int c = 0;
                for (AggregateDataColumn column : columns) {
                    AggregatedDataType dataType = column.getColumnType();
                    if (dataType != null) {
                        if (!dataType.isText() && dataTypesForFooter.contains(dataType)) {
                            if (totalRow.getData(dataType) != null) {
                                table.set(r, c, totalRow.getData(dataType).toString());
                            }
                        }
                        c++;
                        types.add(getExcelType(dataType, resolutionId));
                    } else {
                        c++;
                        QualityStoreDataType type = column.getQualityStoreDataType();
                        types.add(getExcelType(type, resolutionId));
                    }

                }
            }
        }

        File file = null;
        try {
            String fileName = StringUtil.isBlank(reportPreset.getPresetName()) ? "Traffic and Profitability" : reportPreset.getPresetName();
            fileName = fileName.concat(
                    "_" + from.toDateTime().toString("yyyy-MM-dd HH:mm") + "-" + to.toDateTime().toString("yyyy-MM-dd HH:mm") + "." + exportOption.getExtension());
            file = SF.file().createTempFileWithName(cid, fileName);
            if (table != null) {
                if (exportOption == CustomReportExportType.EXCEL) {
                    ExcelUtil.toExcel(file, "Traffic and Profitability Report", getFilterString(reportPreset, dateTimeFormatter, from, to, cid), table, types, 1);
                } else if (exportOption == CustomReportExportType.CSV) {
                    CsvUtil.toCsv(file, table);
                }
            }
        } catch (Exception e) {
            SF.l(getClass(), cid, e);
        }
        return file;
    }

    private String getFilterString(ReportPreset reportPreset, DateTimeFormatter dateTimeFormat, SafeDate from, SafeDate to, int cid) {
        HashMap<ReportPresetFilterType, String> filters = reportPreset.getFilters();
        if (filters == null || filters.size() < 1) {
            return "No filters applied to report";
        }

        int count = 0;
        StringBuilder sb = new StringBuilder();
        DatePeriodType periodType = null;
        ArrayList<ReportPresetFilterType> keys = new ArrayList<>(filters.keySet());
        Collections.sort(keys);
        for (ReportPresetFilterType filterType : keys) {

            String value = filters.get(filterType);

            if (filterType == ReportPresetFilterType.DISPLAY || filterType == ReportPresetFilterType.DISPLAY_OPTION) {
                count++;
                continue;
            } else {
                if (filterType == ReportPresetFilterType.PERIOD_PRESET) {
                    try {
                        periodType = DatePeriodType.get(value);
                        if (periodType != DatePeriodType.CUSTOM) {
                            sb.append(ReportPresetFilterTypeLanguage.get(cid, filterType) + ": ");
                        }
                    } catch (Exception e) {
                    }
                } else {
                    sb.append(ReportPresetFilterTypeLanguage.get(cid, filterType) + ": ");
                }
            }

            if (filterType == ReportPresetFilterType.ROUTING_CLASS) {
                String[] splitted = value.split(",");
                ArrayList<Route> routes = SF.routing().getRouteList(cid, true);
                for (int i = 0; i < splitted.length; i++) {
                    String crid = splitted[i];

                    try {
                        Integer cridValue = Integer.parseInt(crid);
                        for (int j = 0; j < routes.size(); j++) {
                            Route r = routes.get(j);
                            if (r.getCrid() == cridValue) {
                                sb.append(r.getTitle());
                            }
                            if (i + 1 != splitted.length) {
                                sb.append(",");
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            } else if (filterType == ReportPresetFilterType.PERIOD) {
                try {
                    Integer i = Integer.parseInt(value);
                    TrafficTrendsResolution ttr = TrafficTrendsResolution.get(i);
                    sb.append(getTimeString(ttr, cid));
                } catch (Exception e) {
                }
            } else if (filterType == ReportPresetFilterType.TRAFFIC_DIRECTION) {
                TrafficDirection td = TrafficDirection.get(value);
                if (td == TrafficDirection.INCOMING) {
                    sb.append(SF.i18n(cid).incoming());
                } else {
                    sb.append(SF.i18n(cid).outgoing());
                }
            } else if (filterType == ReportPresetFilterType.TRAFFIC_FORMAT) {
                TrafficFormat tf = TrafficFormat.get(value);
                String str;
                if (tf == TrafficFormat.SMS) {
                    str = SF.i18n(cid).sms();
                } else if (tf == TrafficFormat.DATA) {
                    str = SF.i18n(cid).data();
                } else {
                    str = SF.i18n(cid).voice();
                }
                sb.append(str);
            } else if (filterType == ReportPresetFilterType.REVERSE_CHARGING || filterType == ReportPresetFilterType.OUTSIEZED
                    || filterType == ReportPresetFilterType.INCOMING_BILATERAL_DEAL || filterType == ReportPresetFilterType.OUTGOING_BILATERAL_DEAL) {
                try {
                    Boolean b = Boolean.parseBoolean(value);
                    sb.append(b ? "Yes" : "No");
                } catch (Exception e) {
                }
            } else if (filterType == ReportPresetFilterType.EXCHANGE_RATE) {
                ProfitabilityExchangeRateType pert = ProfitabilityExchangeRateType.getType(value);
                if (pert == ProfitabilityExchangeRateType.DAILY) {
                    sb.append(SF.i18n(cid).daily());
                } else if (pert == ProfitabilityExchangeRateType.AVERAGE) {
                    sb.append(SF.i18n(cid).average());
                } else if (pert == ProfitabilityExchangeRateType.END_DATE) {
                    sb.append(SF.i18n(cid).endDate());
                } else if (pert == ProfitabilityExchangeRateType.START_DATE) {
                    sb.append(SF.i18n(cid).startDate());
                }
            } else if ((periodType != null && periodType == DatePeriodType.CUSTOM) && filterType == ReportPresetFilterType.FROM) {
                sb.append(from.toDateTime().toString(dateTimeFormat));
            } else if ((periodType != null && periodType == DatePeriodType.CUSTOM) && filterType == ReportPresetFilterType.TO) {
                sb.append(to.toDateTime().toString(dateTimeFormat));
            } else if (filterType == ReportPresetFilterType.PERIOD_PRESET) {

                switch (periodType) {
                    case LAST_2_WEEKS:
                        sb.append(SF.i18n(cid).lastNWeeks("2"));
                        break;
                    case LAST_30_DAYS:
                        sb.append(SF.i18n(cid).lastNDays("30"));
                        break;
                    case LAST_7_DAYS:
                        sb.append(SF.i18n(cid).lastNDays("7"));
                        break;
                    case LAST_MONTH:
                        sb.append(SF.i18n(cid).lastMonth());
                        break;
                    case LAST_WEEK:
                        sb.append(SF.i18n(cid).lastWeek());
                        break;
                    case THIS_MONTH:
                        sb.append(SF.i18n(cid).thisMonth());
                        break;
                    case THIS_WEEK:
                        sb.append(SF.i18n(cid).thisWeek());
                        break;
                    case TODAY:
                        sb.append(SF.i18n(cid).today());
                        break;
                    case YESTERDAY:
                        sb.append(SF.i18n(cid).yesterday());
                        break;

                    default:
                        break;
                }

            } else {
                sb.append(value);
            }

            if (sb.length() != 0 && ((filterType == ReportPresetFilterType.PERIOD_PRESET && periodType != null && periodType != DatePeriodType.CUSTOM)
                    || count + 1 < filters.size())) {
                sb.append(", ");
            }

            count++;
        }
        int lastIndexOf = sb.lastIndexOf(",");
        if (lastIndexOf != -1) {
            StringBuilder tmp = new StringBuilder(sb.toString().trim());
            if (tmp.length() == (lastIndexOf + 1)) {
                sb = sb.replace(lastIndexOf, sb.length(), "");

            }
        }

        return sb.toString();
    }

    private String getTimeString(TrafficTrendsResolution res, int cid) {

        switch (res) {
            case MINUTES_5:
                return SF.i18n(cid).xMinutes("5");
            case MINUTES_15:
                return SF.i18n(cid).xMinutes("15");
            case HOURLY:
                return SF.i18n(cid).hourly();
            case HOURS_3:
                return SF.i18n(cid).xHours("3");
            case HOURS_6:
                return SF.i18n(cid).xHours("6");
            case HOURS_12:
                return SF.i18n(cid).xHours("12");
            case DAILY:
                return SF.i18n(cid).daily();
            case WEEKLY:
                return SF.i18n(cid).weekly();
            case MONTHLY:
                return SF.i18n(cid).monthly();
            default:
                return "";
        }
    }

    private SimpleEntry<Table, ArrayList<ExcelStyleType>> createHorizontalTable(int cid, ReportPreset reportPreset, PartialList<ArrayList<AggregatedDataGridRow>> report,
                                                                                TrafficTrendsResolution resolution) {

        boolean includeStatusCodes = reportPreset.hasColumn(AggregatedDataType.STATUS_CODES)
                || reportPreset.getFilters().containsKey(ReportPresetFilterType.STATUS_CODES);

        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<AggregatedDataType> textColumns = new ArrayList<>();
        ArrayList<AggregatedDataType> valueColumns = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = getDateTimeFormatter(resolution);
        ArrayList<ExcelStyleType> types = new ArrayList<>();
        LinkedList<AggregateDataColumn> columns = reportPreset.getColumnList();
        for (AggregateDataColumn column : columns) {
            AggregatedDataType adt = column.getColumnType();
            if (!adt.isText() || (adt == AggregatedDataType.DATE || adt == AggregatedDataType.HOUR_OF_DAY || adt == AggregatedDataType.DAY_OF_WEEK
                    || adt == AggregatedDataType.WEEK_OF_YEAR || adt == AggregatedDataType.MONTH || adt == AggregatedDataType.YEAR)) {
                continue;
            }
            columnNames.add(column.getName());
            textColumns.add(adt);
            types.add(getExcelType(adt, 1));
        }

        ArrayList<ArrayList<AggregatedDataGridRow>> dataList = report.getList();
        Table table = null;
        if (dataList != null && dataList.size() > 0) {
            int trendSize = dataList.get(0).size();
            LinkedHashMap<SafeDate, LinkedHashMap<AggregatedDataType, AggregatedDataGridRow>> totalRows = new LinkedHashMap<>();
            for (int i = 0; i < trendSize; i++) {

                for (AggregateDataColumn column : columns) {
                    AggregatedDataType adt = column.getColumnType();
                    if (!adt.isText()) {
                        if (!valueColumns.contains(adt)) {
                            valueColumns.add(adt);
                        }

                        columnNames.add(dataList.get(0).get(i).getDate().toDateTime().toString("dd/MM/yyyy HH:mm") + " " + column.getName());
                        types.add(getExcelType(adt, 1));
                    }
                }
            }
            for (AggregatedDataType adt : valueColumns) {
                // add type total columns
                if (adt != AggregatedDataType.STATUS_CODES) {
                    for (AggregateDataColumn column : columns) {
                        if (column.getColumnType() == adt) {
                            columnNames.add(SF.i18n(cid).total() + " " + column.getName());
                            types.add(getExcelType(adt, 1));
                        }
                    }
                }
            }
            table = new Table();
            table.addColumn(columnNames.toArray(new String[columnNames.size()]));
            for (ArrayList<AggregatedDataGridRow> trendList : dataList) {
                int r = table.addRow();
                int c = 0;
                for (AggregatedDataType textType : textColumns) {
                    Object data = trendList.get(0).getData(textType);
                    String strVal = formatDataForType(textType, data, dateTimeFormatter);
                    table.set(r, c++, strVal);
                }
                int valueColsStart = c;
                AggregatedDataGridRow totalRow = null;
                for (int v = 0; v < valueColumns.size(); v++) {
                    BigDecimal typeSum = BigDecimal.ZERO;

                    AggregatedDataType valueType = valueColumns.get(v);
                    c = valueColsStart + v;
                    for (AggregatedDataGridRow row : trendList) {
                        LinkedHashMap<AggregatedDataType, AggregatedDataGridRow> trendTotals = totalRows.getOrDefault(row.getDate(), new LinkedHashMap<>());
                        totalRow = trendTotals.getOrDefault(valueType, new AggregatedDataGridRow());
                        totalRow.addAggregatedDataGridRow(row, includeStatusCodes);
                        trendTotals.put(valueType, totalRow);
                        totalRows.put(row.getDate(), trendTotals);

                        Object data = row.getData(valueType);
                        String strVal = formatDataForType(valueType, data, dateTimeFormatter);
                        if (valueType != AggregatedDataType.STATUS_CODES) {
                            BigDecimal value = data == null ? BigDecimal.ZERO : new BigDecimal(data.toString());
                            typeSum = typeSum.add(value);
                        }

                        table.set(r, c, strVal);
                        c += valueColumns.size();
                    }
                    table.set(r, c, typeSum.toString());

                }
            }
            // Put sum values into table
            int r = table.addRow();
            int c = textColumns.size();
            for (Entry<SafeDate, LinkedHashMap<AggregatedDataType, AggregatedDataGridRow>> totalTrendEntry : totalRows.entrySet()) {
                for (Entry<AggregatedDataType, AggregatedDataGridRow> totalRowEntry : totalTrendEntry.getValue().entrySet()) {
                    AggregatedDataType adt = totalRowEntry.getKey();
                    if (adt != AggregatedDataType.STATUS_CODES) {
                        table.set(r, c, totalRowEntry.getValue().getData(adt) == null ? "0" : totalRowEntry.getValue().getData(adt).toString());
                    }
                    c++;
                }
            }
            for (AggregatedDataType adt : valueColumns) {
                BigDecimal wholeSum = BigDecimal.ZERO;
                for (Entry<SafeDate, LinkedHashMap<AggregatedDataType, AggregatedDataGridRow>> totalRowData : totalRows.entrySet()) {
                    AggregatedDataGridRow aggregatedDataGridRow = totalRowData.getValue().get(adt);
                    Object data = aggregatedDataGridRow.getData(adt);
                    if (adt != AggregatedDataType.STATUS_CODES) {
                        BigDecimal value = data == null ? BigDecimal.ZERO : new BigDecimal(data.toString());
                        wholeSum = wholeSum.add(value);
                    }
                }
                table.set(r, c++, wholeSum.toString());

            }
        }

        return new SimpleEntry<>(table, types);
    }

    public void share(int cid, SafeDate from, SafeDate end, ReportPreset reportPreset, Order[] order, HashSet<String> to, String subject, String body, int uid, String ip,
                      CustomReportExportType exportOption) {

        PartialList<ArrayList<AggregatedDataGridRow>> report = report(cid, from, end, reportPreset, null, order, uid, ip, true);
        File file = createFileFromReportPreset(cid, from, end, reportPreset, report, exportOption);
        if (file != null) {
            LocalUser user = SF.user().getUser(uid);
            SmtpProperties sp = SF.d().smtp(cid, SmtpSettingType.DEFAULT);
            try {
                HtmlEmail mailer = new HtmlEmail(sp);
                for (String recipient : to) {
                    recipient = recipient.trim();
                    if (recipient.length() == 0) {
                        continue;
                    }
                    mailer.addTo(recipient);
                }
                mailer.setCharset("UTF-8");
                mailer.setSubject(subject);
                mailer.setFrom(sp.getFromEmail(), sp.getFromName());
                mailer.addReplyTo(user.getEmail(), user.getDisplayName());

                mailer.setTemplateUrl(SF.d().getUrl(cid) + "/email/share_" + SF.d().getLocaleCode(cid) + ".jsp", "userName", user.getDisplayName(), "message",
                        (body == null ? "" : body), "baseUrl", SF.d().getUrl(cid));
                String filePath = file.getPath();
                if (filePath != null) {
                    EmailAttachment attachment = new EmailAttachment();
                    attachment.setPath(filePath);
                    attachment.setDisposition(EmailAttachment.ATTACHMENT);
                    attachment.setName(file.getName());
                    mailer.attach(attachment);
                }
                SF.l(getClass(), cid, "Sending " + reportPreset.getPresetName() + "to " + StringUtil.toString(to));
                mailer.send();
            } catch (EmailException e) {
                AlertUtil logUtil = new AlertUtil();
                logUtil.raiseSmtpAlert(cid, uid, e, to, sp.getHost(), sp.getPort());
            } catch (Throwable t) {
                SF.l(getClass(), cid, t);
            }
        }
    }

    private boolean checkFilter(String searchTerm, Set<String> names) {
        boolean filter = true;
        for (String value : names) {
            if (StringUtil.isBlank(value)) {
                continue;
            }
            filter = checkFilter(searchTerm, value);
            if (filter) {
                return filter;
            }
        }
        return filter;
    }

    private boolean checkFilter(String searchTerm, String value) {
        boolean filter = true;
        boolean endsWith = searchTerm.endsWith("*");
        boolean startsWith = searchTerm.startsWith("*");
        String st = searchTerm.replace("*", "").toLowerCase();
        if (!StringUtil.isBlank(searchTerm)) {
            if (startsWith && endsWith) {
                filter = value.toLowerCase().contains(st);
            } else if (startsWith) {
                filter = value.toLowerCase().endsWith(st);
            } else if (endsWith) {
                filter = value.toLowerCase().startsWith(st);
            } else {
                filter = value.toLowerCase().equalsIgnoreCase(st);
            }
        }
        return filter;
    }

    public ArrayList<String> getSwitchNames(int cid) {
        ArrayList<String> switchNames = new ArrayList<>();
        Ni ni = NF.ni(cid);

        if (ni.getNiType().canProvideTrunks()) {
            for (Integer sid : ni.getSids()) {
                switchNames.add(ni.getSwitchName(sid));
            }
        }

        return switchNames;

    }

    public TrunkAndIpSets getTrunkAndIpSets(int cid) {
        TrunkAndIpSets trunkAndIpSets = new TrunkAndIpSets();
        trunkAndIpSets.setSrcTrunkSet(getSourceTrunkList(cid));
        trunkAndIpSets.setDstTrunkSet(getDestinationTrunkList(cid));
        return trunkAndIpSets;
    }

    public HashSet<String> getSourceTrunkList(int cid) {
        return NF.ni(cid).getSrcTrunkNames("");
    }

    public HashSet<String> getDestinationTrunkList(int cid) {
        return NF.ni(cid).getDstTrunkNames("");
    }

    private ExcelStyleType getExcelType(AggregatedDataType dataType, Integer resolutionId) {
        ExcelStyleType excelType = ExcelStyleType.STRING;
        switch (dataType) {
            case ACD:
            case DURATION:
            case INCOMING_LENGTH:
            case INCOMING_MINUTES:
            case OUTGOING_LENGTH:
            case OUTGOING_MINUTES:
            case PDD:
            case PROFIT:
            case TOTAL_COST:
            case TOTAL_REVENUE:
            case REVENUE_OR_COST:
            case ORIGINAL_COST:
            case ORIGINAL_REVENUE:
            case OPERATIONAL_COST:
            case OPERATIONAL_PROFIT:
            case OPERATIONAL_REVENUE:
                excelType = ExcelStyleType.DOUBLE;
                break;
            case ANSWERED_CALLS:
            case CALL_ATTEMPTS:
            case DURATION_SEC:
            case SUCCESSFUL_CALLS:
            case UNSUCCESSFUL_CALLS:
            case HOUR_OF_DAY:
            case WEEK_OF_YEAR:
                excelType = ExcelStyleType.INTEGER;
                break;
            case ASR:
            case NER:
            case PROFIT_MARGIN:
            case OPERATIONAL_PROFIT_MARGIN:
                excelType = ExcelStyleType.PERCENT;
                break;
            case CPM:
            case PPM:
            case RPM:
            case RPM_OR_CPM:
            case INCOMING_RATE:
            case INCOMING_RATE_PER_CALL:
            case OUTGOING_RATE:
            case OUTGOING_RATE_PER_CALL:
            case INCOMING_OPERATIONAL_RATE:
            case OUTGOING_OPERATIONAL_RATE:
                excelType = ExcelStyleType.RATE;
                break;
            case DATE:
                if (resolutionId == 1 || resolutionId == 2 || resolutionId == 3 || resolutionId == 4 || resolutionId == 8 || resolutionId == 9) {
                    excelType = ExcelStyleType.DATE;
                } else {
                    excelType = ExcelStyleType.DATE_SHORT;
                }
                break;
            default:
                break;
        }

        return excelType;
    }

    private ExcelStyleType getExcelType(QualityStoreDataType dataType, Integer resolutionId) {
        ExcelStyleType excelType = ExcelStyleType.STRING;

        if (!dataType.isString() && !dataType.isPercentage()) {
            return excelType;
        }

        switch (dataType) {
            case MOS:
            case CLI_RATE:
            case VIBER_RATE:
            case PRE_CHARGING_RATE:
                excelType = ExcelStyleType.DOUBLE;
                break;
            case CALLS:
            case MOS_CALLS:
            case CLI_CALLS:
            case VIBER_CALLS:
            case PRECHARGING_CALLS:
                excelType = ExcelStyleType.INTEGER;
                break;
            default:
                break;
        }

        return excelType;
    }

    private String getCountry(PrefixGroups prefixGroups, String name) {
        String country = null;
        try {
            for (Country c : prefixGroups.getCountriesRight(name)) {
                country = c.getEnglishShortName();
                break;
            }
        } catch (Exception e) {
            country = Country.UNKNOWN.getEnglishShortName();
        }
        return country != null ? country.toUpperCase() : Country.UNKNOWN.getEnglishShortName();
    }

    private void calculateExchangeRateDates(DateRange dr, ProfitabilityExchangeRateType exchangeRateType) {
        if (exchangeRateType != null && (exchangeRateType == ProfitabilityExchangeRateType.AVERAGE || exchangeRateType == ProfitabilityExchangeRateType.DAILY)) {
            DateTime iteratingOn = dr.getFrom().toDateTime().withTimeAtStartOfDay();
            DateTime end = dr.getTo().toDateTime();
            while (!iteratingOn.isAfter(end)) {
                dailyDateTime.put(iteratingOn.getDayOfYear(), iteratingOn);
                iteratingOn = iteratingOn.plusDays(1);
            }
        }
    }

    private double getExchangeRate(int cid, ProfitabilityExchangeRateType exchangeRateType, DateRange range, DateRange originalRange, Currency src, Currency dest) {
        if (exchangeRateType == ProfitabilityExchangeRateType.AVERAGE) {
            String key = range.getFrom() + "-" + range.getTo() + "-" + src.getTitle() + "-" + dest.getTitle();
            Double exchangeRate = averageExchangeRatesCache.getIfPresent(key);
            if (exchangeRate == null) {
                double sumOfExchangeRates = 0;
                for (DateTime dateTime : dailyDateTime.values()) {
                    sumOfExchangeRates += SF.exchange().getExchangeRate(cid, dateTime, src, dest);
                }
                exchangeRate = sumOfExchangeRates / dailyDateTime.size();
                averageExchangeRatesCache.put(key, exchangeRate);
            }
            return exchangeRate;

        } else if (exchangeRateType == ProfitabilityExchangeRateType.END_DATE) {
            return SF.exchange().getExchangeRate(cid, originalRange.getTo(), src, dest);
        } else if (exchangeRateType == ProfitabilityExchangeRateType.START_DATE) {
            return SF.exchange().getExchangeRate(cid, originalRange.getFrom(), src, dest);
        } else if (exchangeRateType == ProfitabilityExchangeRateType.MONTHLY) {
            DateTime date = range.getFrom().toDateTime().withDayOfMonth(1).plusMonths(1);
            if (date.isAfter(originalRange.getTo().toDateTime())) {
                date = originalRange.getTo().toDateTime();
            } else {
                date = date.minusDays(1);
            }

            return SF.exchange().getExchangeRate(cid, date, src, dest);
        } else {
            return SF.exchange().getExchangeRate(cid, dailyDateTime.get(range.getTo().toDateTime().getDayOfYear()), src, dest);
        }

    }

    public void cancel(int uid, String uniqueKey) {
        cancelledReportKeys.add(uid + "-" + uniqueKey);
    }

    public void shareSummaryCdr(int cid, int uid, String ip, CdrQueryParameters parameters, boolean isAccountMgrRelated, HashSet<String> recipients, String subject,
                                String message) {

        File file = createFileFromCdrQuery(cid, uid, ip, parameters, isAccountMgrRelated);
        if (file != null) {
            LocalUser user = SF.user().getUser(uid);
            SmtpProperties sp = SF.d().smtp(cid, SmtpSettingType.DEFAULT);
            try {
                HtmlEmail mailer = new HtmlEmail(sp);
                for (String recipient : recipients) {
                    recipient = recipient.trim();
                    if (recipient.length() == 0) {
                        continue;
                    }
                    mailer.addTo(recipient);
                }
                mailer.setCharset("UTF-8");
                mailer.setSubject(subject);
                mailer.setFrom(sp.getFromEmail(), sp.getFromName());
                mailer.addReplyTo(user.getEmail(), user.getDisplayName());

                mailer.setTemplateUrl(SF.d().getUrl(cid) + "/email/share_" + SF.d().getLocaleCode(cid) + ".jsp", "userName", user.getDisplayName(), "message",
                        (message == null ? "" : message), "baseUrl", SF.d().getUrl(cid));
                String filePath = file.getPath();
                if (filePath != null) {
                    EmailAttachment attachment = new EmailAttachment();
                    attachment.setPath(filePath);
                    attachment.setDisposition(EmailAttachment.ATTACHMENT);
                    attachment.setName(file.getName());
                    mailer.attach(attachment);
                }
                mailer.send();
            } catch (EmailException e) {
                AlertUtil logUtil = new AlertUtil();
                logUtil.raiseSmtpAlert(cid, uid, e, recipients, sp.getHost(), sp.getPort());
            } catch (Throwable t) {
                SF.l(getClass(), cid, t);
            }
        }
    }

    public ExportResult exportSummaryCdr(int cid, int uid, String ip, CdrQueryParameters parameters, boolean isAccountMgrRelated) {
        File file = createFileFromCdrQuery(cid, uid, ip, parameters, isAccountMgrRelated);
        if (file != null) {
            return new ExportResult(SF.file().getDownloadUrl(cid, file), file.getName(), file.getPath(), false, CustomReportExportType.CSV);

        }
        return null;
    }

    private File createFileFromCdrQuery(int cid, int uid, String ip, CdrQueryParameters parameters, boolean isAccountMgrRelated) {
        NoType noType = NF.no(cid).getNoType();
        boolean hasPdd = noType.hasPostDialDelay();
        boolean hasPartialSequence = noType.hasPartialSequence();
        boolean hasSipResponseCode = noType.lxswSectionsEnabled();
        boolean hasRoutingDecisionPrefixEnabled = noType.lxswSectionsEnabled();
        boolean hasRatePrivilege = SF.user().getUser(uid).hasPrivilege(ConnectPrivilege.CDR_ARCHIVE_RATES);
        ArrayList<Cdr> cdrList = getCdrList(cid, uid, isAccountMgrRelated, parameters);
        Table table = new Table();

        table.addColumn("CDR ID");
        table.addColumn(SF.i18n(cid).start());
        table.addColumn(SF.i18n(cid).finish());
        table.addColumn(SF.i18n(cid).aNumber());
        boolean outseizeNumberEnabled = SF.d().isOutseizeNumberEnabled(cid);
        if (outseizeNumberEnabled) {
            table.addColumn(SF.i18n(cid).outseizeANumber());
        }
        table.addColumn(SF.i18n(cid).bNumber());

        table.addColumn(SF.i18n(cid).duration());
        if (hasPdd) {
            table.addColumn(SF.i18n(cid).pdd());
        }
        table.addColumn(SF.i18n(cid).status());
        if (hasSipResponseCode) {
            table.addColumn(SF.i18n(cid).sipResponseCode());
        }

        table.addColumn(SF.i18n(cid).destination());
        table.addColumn(SF.i18n(cid).origin());
        table.addColumn(SF.i18n(cid).incCarrier());
        table.addColumn(SF.i18n(cid).outCarrier());
        table.addColumn(SF.i18n(cid).srcTrunk());
        table.addColumn(SF.i18n(cid).dstTrunk());

        table.addColumn(SF.i18n(cid).incoming() + " " + SF.i18n(cid).prefix());
        table.addColumn(SF.i18n(cid).incoming() + " " + SF.i18n(cid).destination());
        table.addColumn(SF.i18n(cid).incoming() + " Location");

        table.addColumn(SF.i18n(cid).outgoing() + " " + SF.i18n(cid).prefix());
        table.addColumn(SF.i18n(cid).outgoing() + " " + SF.i18n(cid).destination());
        table.addColumn(SF.i18n(cid).outgoing() + " Location");

        table.addColumn(SF.i18n(cid).incIncrement());
        table.addColumn(SF.i18n(cid).incLength());
        table.addColumn(SF.i18n(cid).outIncrement());
        table.addColumn(SF.i18n(cid).outLength());

        if (hasRatePrivilege) {
            table.addColumn(SF.i18n(cid).incomingRate());
            table.addColumn(SF.i18n(cid).incoming() + " " + SF.i18n(cid).total());
            table.addColumn(SF.i18n(cid).incCurrency());
            table.addColumn(SF.i18n(cid).outgoingRate());
            table.addColumn(SF.i18n(cid).outgoing() + " " + SF.i18n(cid).total());
            table.addColumn(SF.i18n(cid).outCurrency());

            table.addColumn(SF.i18n(cid).incOrgCurrency());
            table.addColumn(SF.i18n(cid).outOrgCurrency());
            table.addColumn(SF.i18n(cid).incToIncExchangeRate());
            table.addColumn(SF.i18n(cid).outToOutExchangeRate());
        }
        table.addColumn(SF.i18n(cid).incTime());
        table.addColumn(SF.i18n(cid).outTime());

        table.addColumn(SF.i18n(cid).incRateEffectiveFrom());
        table.addColumn(SF.i18n(cid).incRateEffectiveTo());
        table.addColumn(SF.i18n(cid).outRateEffectiveFrom());
        table.addColumn(SF.i18n(cid).outRateEffectiveTo());

        table.addColumn(SF.i18n(cid).trafficFormat());
        table.addColumn(SF.i18n(cid).incRatePerCall());
        table.addColumn(SF.i18n(cid).outRatePerCall());

        table.addColumn(SF.i18n(cid).prefix());
        table.addColumn(SF.i18n(cid).incomingOrigin());
        table.addColumn(SF.i18n(cid).incomingOriginDialcode());
        table.addColumn(SF.i18n(cid).outgoingOrigin());
        table.addColumn(SF.i18n(cid).outgoingOriginDialcode());

        if (hasPartialSequence) {
            table.addColumn(SF.i18n(cid).firstCdr());
        }

        if (hasRoutingDecisionPrefixEnabled) {
            table.addColumn(SF.i18n(cid).routingDecisionPrefix());
        }
        ReverseNameLookup destinationRnl = SF.prefixGroups().getPrefixGroups(cid).getRatingDestinationReverseNameLookup();
        ReverseNameLookup originRnl = SF.prefixGroups().getPrefixGroups(cid).getRatingOriginReverseNameLookup();
        int maskCount = SF.d().getCdrMaskCount(cid);
        boolean isMasked = (SF.user().getUser(uid).hasPrivilege(ConnectPrivilege.CDR_ARCHIVE_MASKED)
                && !SF.user().getUser(uid).hasPrivilege(ConnectPrivilege.CDR_ARCHIVE_FULL) || parameters.isMasked());

        int r = 0;
        for (Cdr cdrOriginal : cdrList) {
            Cdr cdr = isMasked ? NF.no(cid).getCdrStore().maskCdr(cdrOriginal, maskCount) : cdrOriginal;
            r = table.addRow();
            int c = 0;

            String incomingCarrierName = SF.carrier().getCarrier(cdr.getInCcid()).getName();
            String outgoingCarrierName = SF.carrier().getCarrier(cdr.getOutCcid()).getName();
            String srcTrunk = cdr.getInTrunk().trim();
            String dstTrunk = cdr.getOutTrunk().trim();
            String prefix = cdr.getPrefix().trim();
            String destination = destinationRnl.getMatch(cdr.getOutNumber(), false, false).getName().toUpperCase();
            String origin = originRnl.getMatch(cdr.getInNumber(), true, true).getName().toUpperCase();

            table.set(r, c++, cdr.getCdrId().toString());
            table.set(r, c++, cdr.getSessionStart() == null ? "" : cdr.getSessionStart().toString());
            table.set(r, c++, cdr.getFinish() == null ? "" : cdr.getFinish().toString());
            table.set(r, c++, cdr.getInNumber());
            if (outseizeNumberEnabled) {
                table.set(r, c++, cdr.getOutseizeInNumber());
            }
            table.set(r, c++, cdr.getOutNumber());

            table.set(r, c++, cdr.getLength() + "");
            if (hasPdd) {
                table.set(r, c++, cdr.getPdd() + "");
            }
            table.set(r, c++, cdr.getStatusCode());
            if (hasSipResponseCode) {
                table.set(r, c++, cdr.getSipResponseCode() + "");
            }
            table.set(r, c++, destination);
            table.set(r, c++, origin);

            table.set(r, c++, incomingCarrierName == null ? "" : incomingCarrierName);
            table.set(r, c++, outgoingCarrierName == null ? "" : outgoingCarrierName);
            table.set(r, c++, srcTrunk == null || srcTrunk.length() == 0 ? "-" : srcTrunk);
            table.set(r, c++, dstTrunk == null || dstTrunk.length() == 0 ? "-" : dstTrunk);

            table.set(r, c++, cdr.getInPrefix());
            table.set(r, c++, cdr.getInDestination());
            table.set(r, c++, cdr.getInLocation());
            table.set(r, c++, cdr.getOutPrefix());
            table.set(r, c++, cdr.getOutDestination());
            table.set(r, c++, cdr.getOutLocation());
            table.set(r, c++, cdr.getInIncrement() == null ? "" : cdr.getInIncrement().toString());
            table.set(r, c++, cdr.getInLength() + "");
            table.set(r, c++, cdr.getOutIncrement() == null ? "" : cdr.getOutIncrement().toString());
            table.set(r, c++, cdr.getOutLength() + "");
            if (hasRatePrivilege) {
                table.set(r, c++, cdr.getInRate() + "");
                table.set(r, c++, cdr.getInTotal() + "");
                table.set(r, c++, cdr.getInCurrency() == null ? "" : cdr.getInCurrency().name());
                table.set(r, c++, cdr.getOutRate() + "");
                table.set(r, c++, cdr.getOutTotal() + "");
                table.set(r, c++, cdr.getOutCurrency() == null ? "" : cdr.getOutCurrency().name());
                table.set(r, c++, cdr.getInOrgCurrency() == null ? "" : cdr.getInOrgCurrency().name());
                table.set(r, c++, cdr.getOutOrgCurrency() == null ? "" : cdr.getOutOrgCurrency().name());
                table.set(r, c++, cdr.getInToInExchangeRate() + "");
                table.set(r, c++, cdr.getOutToOutExchangeRate() + "");
            }
            table.set(r, c++, cdr.getInTime() == null ? "" : cdr.getInTime().name());
            table.set(r, c++, cdr.getOutTime() == null ? "" : cdr.getOutTime().name());
            table.set(r, c++, cdr.getInRateEffectiveFrom() == null ? "" : cdr.getInRateEffectiveFrom().toString());
            table.set(r, c++, cdr.getInRateEffectiveTo() == null ? "" : cdr.getInRateEffectiveTo().toString());
            table.set(r, c++, cdr.getOutRateEffectiveFrom() == null ? "" : cdr.getOutRateEffectiveFrom().toString());
            table.set(r, c++, cdr.getOutRateEffectiveTo() == null ? "" : cdr.getOutRateEffectiveTo().toString());
            table.set(r, c++, cdr.getTrafficFormat().name());
            if (hasRatePrivilege) {
                table.set(r, c++, cdr.getInCallRate() + "");
                table.set(r, c++, cdr.getOutCallRate() + "");
            }
            table.set(r, c++, prefix);
            table.set(r, c++, cdr.getInOrigin());
            table.set(r, c++, cdr.getInOriginPrefix());
            table.set(r, c++, cdr.getOutOrigin());
            table.set(r, c++, cdr.getOutOriginPrefix());

            if (hasPartialSequence) {
                table.set(r, c++, cdr.isFirstCdr() ? SF.i18n(cid).trueLan() : SF.i18n(cid).falseLan());
            }
            if (hasRoutingDecisionPrefixEnabled) {
                table.set(r, c++, cdr.getRoutingDecisionPrefix());
            }
        }

        try {
            File file = SF.file().createTempFileWithName(cid,
                    "Summary_CDR_Report_" + DateTime.now().toString("yyyyMMdd_HHmmss") + "." + CustomReportExportType.CSV.getExtension());
            CsvUtil.toCsv(file, table);
            return file;
        } catch (IOException e) {
            SF.l(getClass(), cid, e);
        }
        return null;
    }

    private ArrayList<Cdr> getCdrList(int cid, int uid, boolean isAccountMgrRelated, CdrQueryParameters parameters) {

        ArrayList<Cdr> cdrList = new ArrayList<>();

        SafeDate fromDate = parameters.getFrom();
        SafeDate toDate = parameters.getTo();
        DateRange dateRange = new DateRange(fromDate.withTimeAtStartOfDay(), toDate.plusDays(1).withTimeAtStartOfDay());

        ReverseNameLookup destinationRnl = SF.prefixGroups().getPrefixGroups(cid).getRatingDestinationReverseNameLookup();
        ReverseNameLookup originRnl = SF.prefixGroups().getPrefixGroups(cid).getRatingOriginReverseNameLookup();
        SwitchNameProvider snp = NF.no(cid).getSwitchNameProvider();
        PrefixGroups prefixGroups = SF.prefixGroups().getPrefixGroups(cid, dateRange.getTo());
        LinkedHashMap<Integer, Carrier> carriers = SF.carrier().getCarriers(cid, null, true, true, true, true, true, "");
        LinkedHashMap<Integer, CarrierGroup> carrierGroups = SF.carrier().getCarrierGroups(cid, true);
        Carrier unknownCarrier = SF.carrier().getUnknownCarrier(cid);
        LinkedHashMap<Integer, LocalUser> users = SF.user().getUsers(cid, true, true, true);
        LinkedHashMap<Integer, HashSet<Country>> routingManagersCountriesMap = SF.routingManager().getRoutingManagersCountriesMap(cid);
        HashMap<String, Integer> countriesToRoutingManagersMap = new HashMap<>();
        ArrayList<Route> routeList = SF.routing().getRouteList(cid, true);

        try {
            //			Prepare time consuming filter data here in order to reduce time when checking aggregated data rows
            for (Entry<Integer, HashSet<Country>> entry : routingManagersCountriesMap.entrySet()) {
                Integer rmId = entry.getKey();
                for (Country country : entry.getValue()) {
                    countriesToRoutingManagersMap.put(country.getEnglishShortName(), rmId);
                }
            }

            ArrayList<PgItem> regions = new ArrayList<>();
            prefixGroups.getNames(null, PgSelector.ADD_REGION).forEach(name -> {
                regions.add(prefixGroups.get(name));
            });
            HashMap<String, String> destinationToRegionMap = new HashMap<>();
            for (PgItem region : regions) {
                for (String dest : region.getSubDestinations()) {
                    destinationToRegionMap.put(dest, region.getName());
                }
            }

            // Generate destination to country names map
            HashMap<String, String> destToCountryMap = new HashMap<>();
            SortedSet<String> destinationNames = prefixGroups.getNames(null, PgSelector.ADD_REFERENCE_DESTINATION, PgSelector.ADD_REFERENCE_DESTINATION,
                    PgSelector.ADD_UNKNOWN, PgSelector.ADD_DATABASE_DRIVEN_DESTINATION, PgSelector.REMOVE_DO_NOT_USE_IN_REPORTS);

            for (String destination : destinationNames) {
                String country = null;
                try {
                    for (Country c : prefixGroups.getCountriesRight(destination)) {
                        country = c.getEnglishShortName();
                        break;
                    }
                } catch (Exception e) {
                    country = Country.UNKNOWN.getEnglishShortName();
                }
                destToCountryMap.put(destination, country != null ? country.toUpperCase() : Country.UNKNOWN.getEnglishShortName());
            }

            HashMap<Integer, String> accountMgrMap = createAccountMgrMap(carriers, users);

            HashSet<String> matchingDestinations = StringUtil.isEmpty(parameters.getParameters().get(AggregatedDataType.DESTINATION)) ? null
                    : new HashSet<>(prefixGroups.getNames(parameters.getParameters().get(AggregatedDataType.DESTINATION), true, PgSelector.ADD_REFERENCE_DESTINATION,
                    PgSelector.ADD_UNKNOWN, PgSelector.ADD_DATABASE_DRIVEN_DESTINATION, PgSelector.REMOVE_DO_NOT_USE_IN_REPORTS));
            HashSet<String> matchingOrigins = StringUtil.isEmpty(parameters.getParameters().get(AggregatedDataType.ORIGIN)) ? null
                    : new HashSet<>(prefixGroups.getNames(parameters.getParameters().get(AggregatedDataType.ORIGIN), true, PgSelector.ADD_REFERENCE_DESTINATION,
                    PgSelector.ADD_UNKNOWN, PgSelector.ADD_DATABASE_DRIVEN_DESTINATION, PgSelector.REMOVE_DO_NOT_USE_IN_REPORTS));

            HashSet<Integer> inFilterCcids = getFilterCcids(carriers, carrierGroups, parameters.getParameters().get(AggregatedDataType.INCOMING_CARRIER), true);
            HashSet<Integer> outFilterCcids = getFilterCcids(carriers, carrierGroups, parameters.getParameters().get(AggregatedDataType.OUTGOING_CARRIER), true);
            int maskCount = SF.d().getCdrMaskCount(cid);
            boolean isMasked = ((SF.user().getUser(uid).hasPrivilege(ConnectPrivilege.CDR_ARCHIVE_MASKED)
                    && !SF.user().getUser(uid).hasPrivilege(ConnectPrivilege.CDR_ARCHIVE_FULL))) || parameters.isMasked();
            NF.no(cid).getCdrStore().parse(null, inFilterCcids, outFilterCcids, dateRange, false, true, false, new CdrStoreParser() {

                @Override
                public long parse(DateTime dt, int rowIndex, Cdr cdr) {
                    if (!(fromDate.isBefore(cdr.getSessionStart()) && toDate.isAfter(cdr.getSessionStart()))) {
                        return -1;
                    }
                    boolean filter = filterCdr(cid, cdr, parameters.getParameters(), destinationRnl, originRnl, snp, prefixGroups, carriers, users,
                            countriesToRoutingManagersMap, routeList, matchingDestinations, matchingOrigins, unknownCarrier, inFilterCcids, outFilterCcids,
                            destToCountryMap, destinationToRegionMap, accountMgrMap);
                    if (!filter) {
                        cdrList.add(isMasked ? NF.no(cid).getCdrStore().maskCdr(cdr, maskCount) : cdr);
                    }
                    return -1;
                }

            });
        } catch (Exception e) {
            SF.l(getClass(), cid, e);
        }

        Collections.sort(cdrList, new Comparator<Cdr>() {

            @Override
            public int compare(Cdr o1, Cdr o2) {
                return o1.getSessionStart().compareTo(o2.getSessionStart());
            }

        });

        return cdrList;
    }

    public CdrAdvancedQueryResult getCdrResultsTable(int cid, int uid, String ip, boolean isAccountMgrRelated, CdrQueryParameters parameters, Range range) {

        ArrayList<CdrSource> cdrSources = cdrSourceCache.getIfPresent(cid);
        if (cdrSources == null) {
            cdrSources = SF.cdr().getCdrSources(cid);
            cdrSourceCache.put(cid, cdrSources);
        }

        ArrayList<Cdr> cdrList = getCdrList(cid, uid, isAccountMgrRelated, parameters);
        SF.l().addLog(cid, uid, ip, null, OperationLogType.VIEW_CDR, null);
        NoType noType = NF.no(cid).getNoType();
        boolean hasPdd = noType.hasPostDialDelay();
        boolean hasPartialSequence = noType.hasPartialSequence();
        boolean hasSipResponseCode = noType.lxswSectionsEnabled();
        boolean hasRatePrivilege = SF.user().getUser(uid).hasPrivilege(ConnectPrivilege.CDR_ARCHIVE_RATES);
        boolean outseizeNumberEnabled = SF.d().isOutseizeNumberEnabled(cid);
        boolean hasRoutingDecisionPrefixEnabled = noType.lxswSectionsEnabled();

        ReverseNameLookup destinationRnl = SF.prefixGroups().getPrefixGroups(cid).getRatingDestinationReverseNameLookup();
        ReverseNameLookup originRnl = SF.prefixGroups().getPrefixGroups(cid).getRatingOriginReverseNameLookup();
        LinkedHashMap<Integer, Carrier> carriers = SF.carrier().getCarriers(cid, null, true, true, true, true, true, "");

        ArrayList<QueryResult> resultSet = new ArrayList<>();

        int listSize = cdrList.size();
        int endIndex = range.getStart() + range.getLength();
        List<Cdr> cdrRange = cdrList.subList(range.getStart(), endIndex > listSize ? listSize : endIndex);

        int maskCount = SF.d().getCdrMaskCount(cid);
        boolean isMasked = (SF.user().getUser(uid).hasPrivilege(ConnectPrivilege.CDR_ARCHIVE_MASKED)
                && !SF.user().getUser(uid).hasPrivilege(ConnectPrivilege.CDR_ARCHIVE_FULL) || parameters.isMasked());

        for (int i = 0; i < range.getLength() && i < cdrList.size(); i++) {

            Cdr cdr = isMasked ? NF.no(cid).getCdrStore().maskCdr(cdrRange.get(i), maskCount) : cdrRange.get(i);
            QueryResult result = new QueryResult();
            StringBuilder cdrDetails = new StringBuilder();

            int k = 0;
            for (CdrReference reference : cdr.getReferences()) {
                cdrDetails.append(cdrSources.get(reference.getSrcId()).getTitle()).append(";").append(cdrSources.get(reference.getSrcId()).getCdrId()).append(";")
                        .append("cdr" + reference.getTable()).append(";").append(reference.getTrackerId());
                if (k != cdr.getReferences().size() - 1) {
                    cdrDetails.append(";");
                }
                k++;
            }
            result.setCdrDetails(cdrDetails.toString());

            String incomingCarrierName = SF.carrier().getCarrier(cdr.getInCcid()).getName();
            String outgoingCarrierName = SF.carrier().getCarrier(cdr.getOutCcid()).getName();
            String srcTrunk = cdr.getInTrunk().trim();
            String dstTrunk = cdr.getOutTrunk().trim();

            if (isAccountMgrRelated) {
                Carrier inCarrier = carriers.get(cdr.getInCcid());
                Carrier outCarrier = carriers.get(cdr.getOutCcid());
                if (!inCarrier.getCombinedAccountManagers().contains(uid)) {
                    incomingCarrierName = "" + inCarrier.getCcid();
                    srcTrunk = "";
                }

                if (!outCarrier.getCombinedAccountManagers().contains(uid)) {
                    outgoingCarrierName = "" + outCarrier.getCcid();
                    dstTrunk = "";
                }
            }
            String prefix = cdr.getPrefix().trim();
            String destination = destinationRnl.getMatch(cdr.getOutNumber(), false, false).getName().toUpperCase();
            String origin = originRnl.getMatch(cdr.getInNumber(), true, true).getName().toUpperCase();
            result.setCdrId(cdr.getCdrId().toString());
            result.setSessionStart(cdr.getSessionStart() == null ? "" : cdr.getSessionStart().toString());
            result.setFinishTime(cdr.getFinish() == null ? "" : cdr.getFinish().toString());
            result.setaNumber(cdr.getInNumber());
            if (outseizeNumberEnabled) {
                result.setOutseizeANumber(cdr.getOutseizeInNumber());
            }
            result.setbNumber(cdr.getOutNumber());
            result.setDuration(cdr.getLength());
            if (hasPdd) {
                result.setPdd(cdr.getPdd());
            }
            result.setStatusCode(cdr.getStatusCode());
            if (hasSipResponseCode) {
                result.setSipResponseCode(cdr.getSipResponseCode());
            }

            result.setDestinaton(destination);
            result.setOrigin(origin);

            result.setIncomingCarrierName(incomingCarrierName == null ? "" : incomingCarrierName);
            result.setOutgoingCarrierName(outgoingCarrierName == null ? "" : outgoingCarrierName);
            result.setIncomingTrunkName(srcTrunk == null || srcTrunk.length() == 0 ? "-" : srcTrunk);

            result.setOutgoingTrunkName(dstTrunk == null || dstTrunk.length() == 0 ? "-" : dstTrunk);

            result.setIncPrefix(cdr.getInPrefix());
            result.setIncDestination(cdr.getInDestination());
            result.setIncLocation(cdr.getInLocation());
            result.setOutPrefix(cdr.getOutPrefix());
            result.setOutDestination(cdr.getOutDestination());
            result.setOutLocation(cdr.getOutLocation());
            result.setIncIncrement(cdr.getInIncrement() == null ? "" : cdr.getInIncrement().toString());
            result.setIncLength(cdr.getInLength());
            result.setOutIncrement(cdr.getOutIncrement() == null ? "" : cdr.getOutIncrement().toString());
            result.setOutLength(cdr.getOutLength());

            if (hasRatePrivilege) {
                result.setIncRate(String.valueOf(cdr.getInRate()));
                result.setIncTotal(String.valueOf(cdr.getInTotal()));
                result.setIncCurrency(cdr.getInCurrency() == null ? "" : cdr.getInCurrency().name());
                result.setOutRate(String.valueOf(cdr.getOutRate()));
                result.setOutTotal(String.valueOf(cdr.getOutTotal()));
                result.setOutCurrency(cdr.getOutCurrency() == null ? "" : cdr.getOutCurrency().name());
                result.setOriginalIncCurrency(cdr.getInOrgCurrency() == null ? "" : cdr.getInOrgCurrency().name());
                result.setOriginalOutCurrency(cdr.getOutOrgCurrency() == null ? "" : cdr.getOutOrgCurrency().name());
                result.setIncToIncExchangeRate(String.valueOf(cdr.getInToInExchangeRate()));
                result.setOutToOutExchangeRate(String.valueOf(cdr.getOutToOutExchangeRate()));

            }
            result.setIncTime(cdr.getInTime() == null ? "" : cdr.getInTime().name());
            result.setOutTime(cdr.getOutTime() == null ? "" : cdr.getOutTime().name());
            result.setIncEffectiveFrom(cdr.getInRateEffectiveFrom() == null ? "" : cdr.getInRateEffectiveFrom().toString());
            result.setIncEffectiveTo(cdr.getInRateEffectiveTo() == null ? "" : cdr.getInRateEffectiveTo().toString());
            result.setOutEffectiveFrom(cdr.getOutRateEffectiveFrom() == null ? "" : cdr.getOutRateEffectiveFrom().toString());
            result.setOutEffectiveTo(cdr.getOutRateEffectiveTo() == null ? "" : cdr.getOutRateEffectiveTo().toString());
            result.setTrafficFormat(cdr.getTrafficFormat());
            if (hasRatePrivilege) {
                result.setIncRatePerCall(String.valueOf(cdr.getInCallRate()));
                result.setOutRatePerCall(String.valueOf(cdr.getOutCallRate()));
            }
            result.setPrefix(prefix);
            result.setIncOrigin(cdr.getInOrigin());
            result.setIncOriginPrefix(cdr.getInOriginPrefix());
            result.setOutOrigin(cdr.getOutOrigin());
            result.setOutOriginPrefix(cdr.getOutOriginPrefix());
            if (hasPartialSequence) {
                result.setFirstCdr(cdr.isFirstCdr());
            }

            if (hasRoutingDecisionPrefixEnabled) {
                result.setRoutingDecisionPrefix(cdr.getRoutingDecisionPrefix());
            }
            resultSet.add(result);
        }

        return new CdrAdvancedQueryResult(resultSet);
    }

    private boolean filterCdr(int cid, Cdr cdr, HashMap<AggregatedDataType, String> parameters, ReverseNameLookup destinationRnl, ReverseNameLookup originRnl,
                              SwitchNameProvider snp, PrefixGroups prefixGroups, LinkedHashMap<Integer, Carrier> carriers, LinkedHashMap<Integer, LocalUser> users,
                              HashMap<String, Integer> countriesToRoutingManagersMap, ArrayList<Route> routeList, HashSet<String> destinations, HashSet<String> origins,
                              Carrier unknownCarrier, HashSet<Integer> inFilterCcids, HashSet<Integer> outFilterCcids, HashMap<String, String> destToCountryMap,
                              HashMap<String, String> destinationToRegionMap, HashMap<Integer, String> accountMgrMap) {
        boolean skipRow = false;
        if (parameters == null || parameters.size() < 1) {
            return skipRow;
        } else {
            for (Entry<AggregatedDataType, String> entry : parameters.entrySet()) {
                boolean filter = true;
                AggregatedDataType key = entry.getKey();
                if (key == AggregatedDataType.CARRIER) {
                    boolean isInCarrier = false;
                    Set<String> ccidSet = new HashSet<>(Arrays.asList(entry.getValue().split(",")));

                    if (checkFilter(cdr.getInCcid() + "", ccidSet)) {
                        isInCarrier = true;
                    }

                    boolean isOutCarrier = false;
                    if (checkFilter(cdr.getOutCcid() + "", ccidSet)) {
                        isOutCarrier = true;
                    }

                    if (isOutCarrier || isInCarrier) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.CARRIER_GROUP) {

                    String direction = parameters.get(AggregatedDataType.TRAFFIC_DIRECTION);
                    TrafficDirection td = null;
                    if (!StringUtil.isBlank(direction)) {
                        td = TrafficDirection.get(direction.substring(0, 1));
                    }
                    boolean isInCarrierGroup = false;
                    boolean isOutCarrierGroup = false;
                    Set<String> ccgidSet = new HashSet<>(Arrays.asList(entry.getValue().split(",")));
                    Carrier inCarrier = carriers.get(cdr.getInCcid());
                    if (inCarrier != null && inCarrier.getCcgid() != null) {
                        if (checkFilter(inCarrier.getCcgid().toString(), ccgidSet)) {
                            isInCarrierGroup = true;
                        }
                    }

                    Carrier outCarrier = carriers.get(cdr.getInCcid());
                    if (outCarrier != null && outCarrier.getCcgid() != null) {
                        if (checkFilter(outCarrier.getCcgid().toString(), ccgidSet)) {
                            isOutCarrierGroup = true;
                        }
                    }

                    if (td == null) {
                        if (isInCarrierGroup || isOutCarrierGroup) {
                            filter = false;
                        }
                    } else if (td == TrafficDirection.INCOMING && isInCarrierGroup) {
                        filter = false;
                    } else if (td == TrafficDirection.OUTGOING && isOutCarrierGroup) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.DATE) {
                    // Covers month, day of week and others
                    String[] range = entry.getValue().split("->");
                    SafeDate from = new SafeDate(range[0].trim());
                    SafeDate to = new SafeDate(range[1].trim());
                    SafeDate cdrStart = new SafeDate(cdr.getSessionStart());
                    if (cdrStart.isEqualOrAfter(from) && cdrStart.isBefore(to)) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.DESTINATION) {
                    if (destinations.contains(destinationRnl.getMatch(cdr.getOutNumber(), false, false).getName())) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.DESTINATION_CATEGORY) {

                    DestinationCategory destinationCategory = getCategory(destinationRnl.getMatch(cdr.getOutNumber(), false, false).getName(), prefixGroups);
                    if (destinationCategory != null) {
                        if (checkFilter(entry.getValue(), destinationCategory.getId())) {
                            filter = false;
                        }
                    }
                } else if (key == AggregatedDataType.DESTINATION_COUNTRY) {

                    String destinationCountry = destToCountryMap.get(destinationRnl.getMatch(cdr.getOutNumber(), false, false).getName());
                    if ((!StringUtil.isBlank(destinationCountry) && checkFilter(entry.getValue(), destinationCountry))) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.ACCOUNT_MANAGER) {
                    if (accountMgrMap != null && accountMgrMap.size() > 0) {
                        String inAccountManagerName = accountMgrMap.get(cdr.getInCcid());
                        String outAccountManagerName = accountMgrMap.get(cdr.getOutCcid());
                        if ((inAccountManagerName != null && !inAccountManagerName.isEmpty() && checkFilter(entry.getValue(), inAccountManagerName))
                                || (outAccountManagerName != null && !outAccountManagerName.isEmpty() && checkFilter(entry.getValue(), outAccountManagerName))) {
                            filter = false;
                        }
                    }
                } else if (key == AggregatedDataType.INCOMING_ACCOUNT_MANAGER) {
                    if (accountMgrMap != null && accountMgrMap.size() > 0) {
                        String accountManagerName = accountMgrMap.get(cdr.getInCcid());
                        if (accountManagerName != null && !accountManagerName.isEmpty() && checkFilter(entry.getValue(), accountManagerName)) {
                            filter = false;
                        }
                    }

                } else if (key == AggregatedDataType.INCOMING_BILATERAL_DEAL) {
                    String filterValue = entry.getValue();
                    if (filterValue != null) {
                        if (TargetRouteType.BILATERAL.name().equalsIgnoreCase(filterValue)) {
                            if (cdr.isInBilateral()) {
                                filter = false;
                            }
                        } else if (TargetRouteType.SWAP.name().equalsIgnoreCase(filterValue)) {
                            if (cdr.isInSwap()) {
                                filter = false;
                            }
                        } else {
                            if (!cdr.isInBilateral() && !cdr.isInSwap()) {
                                filter = false;
                            }
                        }
                    }

                } else if (key == AggregatedDataType.INCOMING_CARRIER) {
                    int ccid = cdr.getInCcid();
                    if (carriers.get(ccid) == null) {
                        ccid = unknownCarrier.getCcid();
                    }
                    if (inFilterCcids.contains(ccid)) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.INCOMING_CARRIER_GROUP) {
                    Set<String> ccgidSet = new HashSet<>(Arrays.asList(entry.getValue().split(",")));
                    Carrier carrier = carriers.get(cdr.getInCcid());
                    if (carrier != null && carrier.getCcgid() != null) {
                        if (checkFilter(carrier.getCcgid().toString(), ccgidSet)) {
                            filter = false;
                        }
                    }
                } else if (key == AggregatedDataType.INCOMING_CURRENCY) {
                    Currency inCurr = Currency.getByName(entry.getValue());
                    if (inCurr == cdr.getInCurrency()) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.INCOMING_SWITCH) {
                    if (checkFilter(entry.getValue(), snp.getSwitchName(cdr.getInTrunk()))) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.INCOMING_TRUNK) {
                    if (checkFilter(entry.getValue(), cdr.getInTrunk())) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.OFFERED_DESTINATION) {
                    if (checkFilter(entry.getValue(), cdr.getOutDestination())) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.OFFERED_ORIGIN) {
                    if (checkFilter(entry.getValue(), cdr.getOutOrigin())) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.ORIGIN) {
                    if (origins.contains(entry.getValue())) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.ORIGIN_CATEGORY) {
                    DestinationCategory originCategory = getCategory(originRnl.getMatch(cdr.getInNumber(), true, true).getName(), prefixGroups);
                    if (originCategory != null) {
                        if (checkFilter(entry.getValue(), originCategory.getId())) {
                            filter = false;
                        }
                    }
                } else if (key == AggregatedDataType.ORIGIN_COUNTRY) {
                    String originCountry = destToCountryMap.get(destinationRnl.getMatch(cdr.getOutNumber(), true, true).getName());
                    if ((!StringUtil.isBlank(originCountry) && checkFilter(entry.getValue(), originCountry))) {
                        filter = false;
                    }

                } else if (key == AggregatedDataType.OUTGOING_ACCOUNT_MANAGER) {
                    if (accountMgrMap != null && accountMgrMap.size() > 0) {
                        String accountManagerName = accountMgrMap.get(cdr.getOutCcid());
                        if (accountManagerName != null && !accountManagerName.isEmpty() && checkFilter(entry.getValue(), accountManagerName)) {
                            filter = false;
                        }
                    }

                } else if (key == AggregatedDataType.OUTGOING_BILATERAL_DEAL) {
                    String filterValue = entry.getValue();
                    if (filterValue != null) {
                        if (TargetRouteType.BILATERAL.name().equalsIgnoreCase(filterValue)) {
                            if (cdr.isOutBilateral()) {
                                filter = false;
                            }
                        } else if (TargetRouteType.SWAP.name().equalsIgnoreCase(filterValue)) {
                            if (cdr.isOutSwap()) {
                                filter = false;
                            }
                        } else {
                            if (!cdr.isOutBilateral() && !cdr.isOutSwap()) {
                                filter = false;
                            }
                        }
                    }

                } else if (key == AggregatedDataType.OUTGOING_CARRIER) {
                    int ccid = cdr.getOutCcid();
                    if (carriers.get(ccid) == null) {
                        ccid = unknownCarrier.getCcid();
                    }
                    if (outFilterCcids.contains(ccid)) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.OUTGOING_CARRIER_GROUP) {
                    Set<String> ccgidSet = new HashSet<>(Arrays.asList(entry.getValue().split(",")));
                    Carrier carrier = carriers.get(cdr.getOutCcid());
                    if (carrier != null && carrier.getCcgid() != null) {
                        if (checkFilter(carrier.getCcgid().toString(), ccgidSet)) {
                            filter = false;
                        }
                    }
                } else if (key == AggregatedDataType.OUTGOING_CURRENCY) {
                    Currency outCurr = Currency.getByName(entry.getValue());
                    if (outCurr == cdr.getOutCurrency()) {
                        filter = false;
                    }

                } else if (key == AggregatedDataType.OUTGOING_SWITCH) {
                    if (checkFilter(entry.getValue(), snp.getSwitchName(cdr.getOutTrunk()))) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.OUTGOING_TRUNK) {
                    if (checkFilter(entry.getValue(), cdr.getOutTrunk())) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.OUTSEIZED_ORIGIN) {
                    if (checkFilter(entry.getValue(), originRnl.getMatch(cdr.getOutseizeInNumber(), true, true).getName())) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.OUTSIEZED) {
                    boolean isOutseized = entry.getValue().equalsIgnoreCase("true");
                    if (isOutseized != (cdr.getOutseizeInNumber().equalsIgnoreCase(cdr.getInNumber()))) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.REVERSE_CHARGING) {
                    try {
                        Boolean isReverseCharge = Boolean.parseBoolean(entry.getValue());
                        if (isReverseCharge == cdr.isReverseCharged()) {
                            filter = false;
                        }
                    } catch (Exception e) {
                    }

                } else if (key == AggregatedDataType.ROUTING_CLASS) {
                    try {
                        Integer crid = Integer.parseInt(entry.getValue());
                        if (crid != null && crid.intValue() == cdr.getCrid()) {
                            filter = false;
                        }
                    } catch (Exception e) {
                    }

                } else if (key == AggregatedDataType.ROUTING_MANAGER) {
                    String destinationCountry = getCountry(prefixGroups, destinationRnl.getMatch(cdr.getOutNumber(), false, false).getName());
                    Integer rmId = countriesToRoutingManagersMap.get(destinationCountry);
                    if (rmId != null) {
                        LocalUser routingManager = users.get(rmId);
                        if (routingManager != null) {
                            if (checkFilter(entry.getValue(), routingManager.getDisplayName())) {
                                filter = false;
                            }
                        }
                    }
                } else if (key == AggregatedDataType.ROUTING_DECISION) {
                    String decision = entry.getValue();

                    Integer decisionCrid = null;
                    for (Route route : routeList) {
                        if (route.getTitle().equals(decision)) {
                            decisionCrid = route.getCrid();
                            break;
                        }
                    }
                    String decisionPrefix = SF.routing().getRoutingDecisionPrefixes(cid).getPrefix(decision);

                    if (decisionPrefix == null) {
                        if (decisionCrid != null) {
                            decisionPrefix = SF.routing().getRoutingDecisionPrefixes(cid).getRoutingClassPrefix(decisionCrid);
                        }
                    }

                    if (decisionPrefix == null && StringUtil.isBlank(cdr.getRoutingDecisionPrefix())) {
                        filter = false;
                    } else if (decisionPrefix != null && cdr.getRoutingDecisionPrefix() != null && decisionPrefix.equals(cdr.getRoutingDecisionPrefix())) {
                        filter = false;
                    } else if (cdr.getRoutingDecisionPrefix() != null
                            && cdr.getRoutingDecisionPrefix().equals(SF.routing().getRoutingDecisionPrefixes(cid).getDefaultRoutingPrefix())) {
                        if (decisionCrid != null && cdr.getCrid() == decisionCrid.intValue()) {
                            filter = false;
                        }
                    }
                } else if (key == AggregatedDataType.SUPPLIER_DESTINATION) {
                    if (checkFilter(entry.getValue(), cdr.getInDestination())) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.SUPPLIER_ORIGIN) {
                    if (checkFilter(entry.getValue(), cdr.getInOrigin())) {
                        filter = false;
                    }
                } else if (key == AggregatedDataType.TRAFFIC_FORMAT) {
                    TrafficFormat tf = TrafficFormat.get(entry.getValue());
                    if (tf == cdr.getTrafficFormat()) {
                        filter = false;
                    }
                } else {
                    continue;
                }
                skipRow |= filter;
                if (skipRow) { // no need to check after this point
                    return skipRow;
                }
            }
            return skipRow;
        }

    }

    private HashSet<AggregatedDataType> getDataTypesForFooter() {
        HashSet<AggregatedDataType> set = new HashSet<>();
        set.add(AggregatedDataType.SUCCESSFUL_CALLS);
        set.add(AggregatedDataType.CALL_ATTEMPTS);
        set.add(AggregatedDataType.ANSWERED_CALLS);
        set.add(AggregatedDataType.TOTAL_COST);
        set.add(AggregatedDataType.TOTAL_REVENUE);
        set.add(AggregatedDataType.PROFIT);
        set.add(AggregatedDataType.PROFIT_MARGIN);
        set.add(AggregatedDataType.ACD);
        set.add(AggregatedDataType.NER);
        set.add(AggregatedDataType.ASR);
        set.add(AggregatedDataType.PDD);
        set.add(AggregatedDataType.RPM);
        set.add(AggregatedDataType.CPM);
        set.add(AggregatedDataType.PPM);
        set.add(AggregatedDataType.DURATION);
        set.add(AggregatedDataType.DURATION_SEC);
        set.add(AggregatedDataType.INCOMING_MINUTES);
        set.add(AggregatedDataType.OUTGOING_MINUTES);
        set.add(AggregatedDataType.OPERATIONAL_COST);
        set.add(AggregatedDataType.OPERATIONAL_REVENUE);
        set.add(AggregatedDataType.OPERATIONAL_PROFIT);
        set.add(AggregatedDataType.OPERATIONAL_PROFIT_MARGIN);
        return set;
    }

    public LinkedHashMap<String, LinkedHashMap<AggregatedDataType, LinkedHashMap<Integer, ArrayList<AggregatedDataGridRow>>>> getWidgetHistoricalComparisonChartReport(
            int cid, int uid, ReportPreset reportPreset) {
        String[] days = new String[] { "today", "yesterday", "last week" };
        LinkedHashMap<String, LinkedHashMap<AggregatedDataType, LinkedHashMap<Integer, ArrayList<AggregatedDataGridRow>>>> result = new LinkedHashMap<>();

        SafeDate today = new SafeDate().withTimeAtStartOfDay();
        SafeDate tomorrow = today.plusDays(1);

        SafeDate from = today;
        SafeDate to = tomorrow;
        result.put(days[0], getDailyData(cid, uid, from, to, reportPreset));

        from = today.minusDays(1);
        to = tomorrow.minusDays(1);
        result.put(days[1], getDailyData(cid, uid, from, to, reportPreset));

        from = today.minusDays(7);
        to = tomorrow.minusDays(7);
        result.put(days[2], getDailyData(cid, uid, from, to, reportPreset));

        return result;
    }

    public LinkedHashMap<AggregatedDataType, LinkedHashMap<Integer, ArrayList<AggregatedDataGridRow>>> getDailyData(int cid, int uid, SafeDate from, SafeDate to,
                                                                                                                    ReportPreset reportPreset) {
        LinkedHashMap<AggregatedDataType, LinkedHashMap<SafeDate, ArrayList<AggregatedDataGridRow>>> sourceDataBySafeDate = getChartData(cid, uid, from, to, reportPreset,
                false);

        LinkedHashMap<AggregatedDataType, LinkedHashMap<Integer, ArrayList<AggregatedDataGridRow>>> dataByHour = new LinkedHashMap<>();
        for (AggregatedDataType dataType : sourceDataBySafeDate.keySet()) {
            LinkedHashMap<Integer, ArrayList<AggregatedDataGridRow>> rawDataList = new LinkedHashMap<>();
            for (SafeDate date : sourceDataBySafeDate.get(dataType).keySet()) {
                if (date != null && sourceDataBySafeDate.get(dataType).get(date) != null) {
                    rawDataList.put(date.getHourOfDay(), sourceDataBySafeDate.get(dataType).get(date));
                }
            }
            dataByHour.put(dataType, rawDataList);
        }
        return dataByHour;
    }

    public LinkedHashMap<AggregatedDataType, LinkedHashMap<SafeDate, ArrayList<AggregatedDataGridRow>>> getChartData(int cid, int uid, SafeDate from, SafeDate to,
                                                                                                                     ReportPreset reportPreset, boolean removeZeroOrNullRows) {
        boolean includeStatusCodes = reportPreset.hasColumn(AggregatedDataType.STATUS_CODES)
                || reportPreset.getFilters().containsKey(ReportPresetFilterType.STATUS_CODES);

        LinkedHashMap<AggregatedDataType, LinkedHashMap<SafeDate, ArrayList<AggregatedDataGridRow>>> linkedResult = new LinkedHashMap<>();

        HashMap<AggregatedDataType, Order[]> orderArrayMap = new HashMap<>();
        HashMap<AggregatedDataType, LinkedList<AggregatedDataType>> orderListMap = new HashMap<>();

        LinkedList<AggregatedDataType> valueColumns = new LinkedList<>();
        LinkedList<AggregatedDataType> textColumns = new LinkedList<>();
        LinkedList<AggregateDataColumn> columns = reportPreset.getColumnList();
        for (AggregateDataColumn column : columns) {
            AggregatedDataType aggregatedDataType = column.getColumnType();
            if (aggregatedDataType == AggregatedDataType.DATE) {
                continue;
            }
            if (aggregatedDataType != null && aggregatedDataType.isText()) {
                textColumns.add(aggregatedDataType);
            } else {
                valueColumns.add(aggregatedDataType);
            }
        }

        for (AggregatedDataType adt : valueColumns) {
            Order[] orderArr = new Order[textColumns.size() + 2];
            LinkedList<AggregatedDataType> typeList = new LinkedList<>();
            typeList.add(AggregatedDataType.DATE);
            typeList.add(adt);
            orderArr[0] = new Order(AggregatedDataType.DATE.name(), true);
            if (adt != null) {
                orderArr[1] = new Order(adt.name(), false);
            }
            for (int i = 0; i < textColumns.size(); i++) {
                AggregatedDataType textDataType = textColumns.get(i);
                orderArr[i + 2] = new Order(textDataType.name(), true);
                typeList.add(textDataType);
            }
            orderListMap.put(adt, typeList);
            orderArrayMap.put(adt, orderArr);
        }
        PartialList<ArrayList<AggregatedDataGridRow>> report = report(cid, from, to, reportPreset, null, null, uid, "127.0.0.1", removeZeroOrNullRows);

        if (report.getList() == null || report.getList().size() <= 0) {
            return linkedResult;
        }
        ArrayList<AggregatedDataGridRow> allRows = new ArrayList<>();
        for (ArrayList<AggregatedDataGridRow> rows : report.getList()) {
            allRows.add(rows.get(0));
        }

        for (Entry<AggregatedDataType, Order[]> entry : orderArrayMap.entrySet()) {
            LinkedHashMap<SafeDate, ArrayList<AggregatedDataGridRow>> result = new LinkedHashMap<>();

            AggregatedDataType columnType = entry.getKey();
            Order[] orderArr = entry.getValue();

            ArrayList<DateRange> ranges = getRanges(from.toDateTime(), to.toDateTime(), reportPreset);
            for (DateRange range : ranges) {

                Collections.sort(allRows, new Comparator<AggregatedDataGridRow>() {

                    @Override
                    public int compare(AggregatedDataGridRow r1, AggregatedDataGridRow r2) {
                        if (r1 == null || r2 == null) {
                            return 0;
                        }
                        for (Order o : orderArr) {
                            int result = r1.compareTo(r2, o);
                            if (result != 0) {
                                return result;
                            }
                        }

                        return 0;
                    }
                });

                LinkedHashMap<String, AggregatedDataGridRow> rows = new LinkedHashMap<>();
                SafeDate prevDate = null;
                int i = 0;
                for (AggregatedDataGridRow aggregatedDataGridRow : allRows) {
                    AggregatedDataGridRow clone = aggregatedDataGridRow.clone();
                    SafeDate date = clone.getDate();
                    if (!(date.isEqualOrAfter(range.getFrom()) && date.isBefore(range.getTo()))) {
                        continue;
                    }

                    prevDate = date;
                    String key = null;
                    if (i < 10) {
                        key = clone.getChartKey(orderListMap.get(columnType));
                        rows.put(key, clone);
                    } else {
                        key = "OTHERS";
                        AggregatedDataGridRow otherRow = rows.get(key);
                        if (otherRow == null) {
                            otherRow = clone;
                        } else {
                            otherRow.addAggregatedDataGridRow(clone, includeStatusCodes);
                        }
                        rows.put(key, otherRow);
                    }
                    i++;
                } // end of merging rows

                // Create total Row
                AggregatedDataGridRow totalRow = null;
                for (AggregatedDataGridRow gridRow : rows.values()) {
                    if (totalRow == null) {
                        totalRow = gridRow.clone();
                    } else {
                        totalRow.addAggregatedDataGridRow(gridRow, includeStatusCodes);
                    }

                }
                rows.put("TOTAL", totalRow);
                ArrayList<AggregatedDataGridRow> rowsList = new ArrayList<>();
                rowsList.addAll(rows.values());
                result.put(prevDate, rowsList);
                linkedResult.put(columnType, result);
                i = 0;
                prevDate = null;
                rows = new LinkedHashMap<>();

            }
        }

        return linkedResult;

    }

    private String formatDataForType(AggregatedDataType dataType, Object data, DateTimeFormatter dateTimeFormatter) {
        String strVal = "";
        if (data != null) {
            if (dataType == AggregatedDataType.DAY_OF_WEEK) {
                Day day = Day.getDay((int) data % 7);
                strVal = day.name();
            } else if (dataType == AggregatedDataType.MONTH) {
                strVal = Month.of((int) data).name();
            } else if (data instanceof Boolean) {
                boolean b = Boolean.parseBoolean(data.toString());
                if (b) {
                    strVal = "Yes";
                } else {
                    strVal = "No";
                }
            } else if (data instanceof DateRange) {
                SafeDate date = ((DateRange) data).getFrom();
                strVal = date.toDateTime().toString(dateTimeFormatter);
            } else if (data instanceof SafeDate) {
                SafeDate date = (SafeDate) data;
                strVal = date.toDateTime().toString(dateTimeFormatter);
            } else {
                strVal = data.toString();
            }
        } else {
            strVal = dataType == AggregatedDataType.STATUS_CODES || dataType == AggregatedDataType.ROUTING_DECISION
                    || dataType == AggregatedDataType.INCOMING_BILATERAL_DEAL || dataType == AggregatedDataType.OUTGOING_BILATERAL_DEAL
                    || dataType == AggregatedDataType.ROUTING_MANAGER ? "-" : "0";
        }
        return strVal;
    }

    private String formatDataForType(QualityStoreDataType dataType, Object data, DateTimeFormatter dateTimeFormatter) {
        String strVal = "";
        if (data != null) {
            if (data instanceof Boolean) {
                boolean b = Boolean.parseBoolean(data.toString());
                if (b) {
                    strVal = "Yes";
                } else {
                    strVal = "No";
                }
            } else {
                strVal = data.toString();
            }
        }
        return strVal;
    }

    private DateTimeFormatter getDateTimeFormatter(TrafficTrendsResolution resolution) {
        if (resolution == null) {
            return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
        }
        switch (resolution) {
            case MONTHLY:
                return DateTimeFormat.forPattern("yyyy-MM-dd");
            case DAILY:
            case WEEKLY:
                return DateTimeFormat.forPattern("yyyy-MM-dd");
            case HOURLY:
            case HOURS_12:
            case HOURS_3:
            case HOURS_6:
            default:
                return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
        }
    }

    private HashSet<Integer> getFilterCcids(LinkedHashMap<Integer, Carrier> carriers, LinkedHashMap<Integer, CarrierGroup> carrierGroups, String filter) {
        return getFilterCcids(carriers, carrierGroups, filter, false);
    }

    private HashSet<Integer> getFilterCcids(LinkedHashMap<Integer, Carrier> carriers, LinkedHashMap<Integer, CarrierGroup> carrierGroups, String filter,
                                            boolean isFilterCcid) {
        HashSet<Integer> ccids = new HashSet<>();
        if (StringUtil.isEmpty(filter)) {
            ccids.addAll(carriers.keySet());
            return ccids;
        }

        if (isFilterCcid) {
            ccids = (HashSet<Integer>) Arrays.asList(filter.split(",")).stream().map(Integer::parseInt).collect(Collectors.toSet());
            return ccids;
        } else {
            for (Carrier carrier : carriers.values()) {
                if (checkFilter(filter, carrier.getName())) {
                    ccids.add(carrier.getCcid());
                }
            }

            for (CarrierGroup cg : carrierGroups.values()) {
                if (checkFilter(filter, cg.getName())) {
                    ccids.addAll(cg.getCcidSet());
                }
            }
        }
        return ccids;
    }

    private HashMap<Integer, String> createAccountMgrMap(LinkedHashMap<Integer, Carrier> carriers, LinkedHashMap<Integer, LocalUser> users) {
        HashMap<Integer, String> map = new HashMap<>();
        for (Carrier carrier : carriers.values()) {
            Integer accountManagerId = carrier.getAccountManager();
            String accountManagerName = "";
            if (accountManagerId != -1) {
                accountManagerName = users.get(accountManagerId).getDisplayName();
            }
            map.put(carrier.getCcid(), accountManagerName);
        }
        return map;
    }

    public Object getCacheLock(String gridKey) {
        Object lock = cacheLocks.get(gridKey);
        if (lock == null) {
            synchronized (cacheLocks) {
                lock = cacheLocks.get(gridKey);
                if (lock == null) {
                    lock = new Object();
                    cacheLocks.put(gridKey, lock);
                }
            }
        }
        return lock;
    }

}
