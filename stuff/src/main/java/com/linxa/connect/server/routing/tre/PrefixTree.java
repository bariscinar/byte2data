package com.linxa.connect.server.routing.tre;

import com.asimalp.commons.shared.model.Country;
import com.asimalp.commons.shared.model.Currency;
import com.linxa.connect.server.route.ReverseNameLookup;
import com.linxa.connect.server.routing.tree.PrefixTreeType;
import com.linxa.connect.shared.model.Target;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PrefixTree implements Serializable, Cloneable {

    private static final long serialVersionUID = 738978992057749230L;

    private PrefixTreeType type;



    private HashSet<Integer> ccids = new HashSet<>();

    private Date buildDate;

    private Integer crid = null;

    private boolean isActive = false;

    private Currency currency;

    private int activationSeq = 0;

    private ArrayList<Target> dealTargets;

    // TODO A- Remove transient after a while. We had to make this transient due to a serialization issue. Once old PrefixTrees are cleaned up,
    // we can make this persistent.
    private transient Set<Country> changedCountries;

    private transient boolean isMinified, isMappedIncoming, isValid, isVolumeLoaded = false, isDealRatesLoaded = false, isRoutingExceptionsLoaded = false;

    private transient Integer joid = null;

    private transient ReverseNameLookup reverseNameLookup;

    private transient HashSet<Integer> incomingMappingDebugCcids = null;

    private PrefixTree(int cid) {

    }

    private PrefixTree(){
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public PrefixTreeType getType() {
        return type;
    }

    public void setType(PrefixTreeType type) {
        this.type = type;
    }


    public HashSet<Integer> getCcids() {
        return ccids;
    }

    public void setCcids(HashSet<Integer> ccids) {
        this.ccids = ccids;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    public Integer getCrid() {
        return crid;
    }

    public void setCrid(Integer crid) {
        this.crid = crid;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getActivationSeq() {
        return activationSeq;
    }

    public void setActivationSeq(int activationSeq) {
        this.activationSeq = activationSeq;
    }

    public ArrayList<Target> getDealTargets() {
        return dealTargets;
    }

    public void setDealTargets(ArrayList<Target> dealTargets) {
        this.dealTargets = dealTargets;
    }

    public Set<Country> getChangedCountries() {
        return changedCountries;
    }

    public void setChangedCountries(Set<Country> changedCountries) {
        this.changedCountries = changedCountries;
    }

    public boolean isMinified() {
        return isMinified;
    }

    public void setMinified(boolean minified) {
        isMinified = minified;
    }

    public boolean isMappedIncoming() {
        return isMappedIncoming;
    }

    public void setMappedIncoming(boolean mappedIncoming) {
        isMappedIncoming = mappedIncoming;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isVolumeLoaded() {
        return isVolumeLoaded;
    }

    public void setVolumeLoaded(boolean volumeLoaded) {
        isVolumeLoaded = volumeLoaded;
    }

    public boolean isDealRatesLoaded() {
        return isDealRatesLoaded;
    }

    public void setDealRatesLoaded(boolean dealRatesLoaded) {
        isDealRatesLoaded = dealRatesLoaded;
    }

    public boolean isRoutingExceptionsLoaded() {
        return isRoutingExceptionsLoaded;
    }

    public void setRoutingExceptionsLoaded(boolean routingExceptionsLoaded) {
        isRoutingExceptionsLoaded = routingExceptionsLoaded;
    }

    public Integer getJoid() {
        return joid;
    }

    public void setJoid(Integer joid) {
        this.joid = joid;
    }

    public ReverseNameLookup getReverseNameLookup() {
        return reverseNameLookup;
    }

    public void setReverseNameLookup(ReverseNameLookup reverseNameLookup) {
        this.reverseNameLookup = reverseNameLookup;
    }

    public HashSet<Integer> getIncomingMappingDebugCcids() {
        return incomingMappingDebugCcids;
    }

    public void setIncomingMappingDebugCcids(HashSet<Integer> incomingMappingDebugCcids) {
        this.incomingMappingDebugCcids = incomingMappingDebugCcids;
    }
}

