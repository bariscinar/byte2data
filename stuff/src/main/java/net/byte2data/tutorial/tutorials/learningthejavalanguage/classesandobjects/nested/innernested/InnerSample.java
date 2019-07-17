package net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.nested.innernested;

/**
 * Created by barisci on 15.09.2017.
 */
public final class InnerSample {

    private static final double privateStaticFinalIntClassField=1.1;
    static final double packagePrivateStaticFinalIntClassField=1.2;
    protected static final double protectedStaticFinalIntClassField=1.3;
    public static final double publicStaticFinalIntClassField=1.4;

    private static double privateStaticIntClassField=2.1;
    static double packagePrivateStaticIntClassField=2.2;
    protected static double protectedStaticIntClassField=2.3;
    public static double publicStaticIntClassField=2.4;

    private final double privateFinalIntInstanceField=3.1;
    final double packagePrivateFinalIntInstanceField=3.2;
    protected final double protectedFinalIntInstanceField=3.3;
    public final double publicFinalIntInstanceField=3.4;

    private double privateIntInstanceField=4.1;
    double packagePrivateIntInstanceField=4.2;
    protected double protectedIntInstanceField=4.3;
    public double publicIntInstanceField=4.4;


    public class PublicInner{
        private static final double privateStaticFinalIntClassField=10.1;
        static final double packagePrivateStaticFinalIntClassField=10.2;
        protected static final double protectedStaticFinalIntClassField=10.3;
        public static final double publicStaticFinalIntClassField=10.4;

        //private static double privateStaticIntClassField=20.1;
        //static double packagePrivateStaticIntClassField=20.2;
        //protected static double protectedStaticIntClassField=20.3;
        //public static double publicStaticIntClassField=20.4;

        private final double privateFinalIntInstanceField=30.1;
        final double packagePrivateFinalIntInstanceField=30.2;
        protected final double protectedFinalIntInstanceField=30.3;
        public final double publicFinalIntInstanceField=30.4;

        private double privateIntInstanceField=40.1;
        double packagePrivateIntInstanceField=40.2;
        protected double protectedIntInstanceField=40.3;
        public double publicIntInstanceField=40.4;

        public PublicInner(){
            InnerSample innerSample = new InnerSample();

            System.out.println(InnerSample.privateStaticFinalIntClassField);
            System.out.println(privateStaticFinalIntClassField);
            System.out.println(PublicInner.privateStaticFinalIntClassField);
            System.out.println(this.privateStaticFinalIntClassField);

            System.out.println(InnerSample.packagePrivateStaticFinalIntClassField);
            System.out.println(InnerSample.protectedStaticFinalIntClassField);
            System.out.println(InnerSample.publicStaticFinalIntClassField);
            System.out.println(InnerSample.privateStaticIntClassField);
            System.out.println(InnerSample.packagePrivateStaticIntClassField);
            System.out.println(InnerSample.protectedStaticIntClassField);
            System.out.println(publicStaticIntClassField);

            System.out.println(privateFinalIntInstanceField);
            System.out.println(this.privateFinalIntInstanceField);
            System.out.println(InnerSample.this.privateFinalIntInstanceField);
            System.out.println(innerSample.privateFinalIntInstanceField);

            System.out.println(packagePrivateFinalIntInstanceField);
            System.out.println(protectedFinalIntInstanceField);
            System.out.println(publicFinalIntInstanceField);
            System.out.println(privateIntInstanceField);
            System.out.println(packagePrivateIntInstanceField);
            System.out.println(protectedIntInstanceField);

            System.out.println(publicIntInstanceField);
            System.out.println(this.publicIntInstanceField);
            //System.out.println(InnerSample.publicIntInstanceField);
            System.out.println(InnerSample.this.publicIntInstanceField);
            System.out.println(innerSample.publicIntInstanceField);
        }
    }


    protected class ProtectedInner{
        public ProtectedInner(){
            System.out.println(privateStaticFinalIntClassField);
            System.out.println(InnerSample.packagePrivateStaticFinalIntClassField);
            System.out.println(InnerSample.protectedStaticFinalIntClassField);
            System.out.println(InnerSample.publicStaticFinalIntClassField);
            System.out.println(InnerSample.privateStaticIntClassField);
            System.out.println(InnerSample.packagePrivateStaticIntClassField);
            System.out.println(InnerSample.protectedStaticIntClassField);
            System.out.println(publicStaticIntClassField);

            System.out.println(privateFinalIntInstanceField);
            System.out.println(packagePrivateFinalIntInstanceField);
            System.out.println(protectedFinalIntInstanceField);
            System.out.println(publicFinalIntInstanceField);
            System.out.println(privateIntInstanceField);
            System.out.println(packagePrivateIntInstanceField);
            System.out.println(protectedIntInstanceField);
            System.out.println(publicIntInstanceField);
        }
    }


    class PackagePrivateInner{
        public PackagePrivateInner(){
            System.out.println(privateStaticFinalIntClassField);
            System.out.println(InnerSample.packagePrivateStaticFinalIntClassField);
            System.out.println(InnerSample.protectedStaticFinalIntClassField);
            System.out.println(InnerSample.publicStaticFinalIntClassField);
            System.out.println(InnerSample.privateStaticIntClassField);
            System.out.println(InnerSample.packagePrivateStaticIntClassField);
            System.out.println(InnerSample.protectedStaticIntClassField);
            System.out.println(publicStaticIntClassField);

            System.out.println(privateFinalIntInstanceField);
            System.out.println(packagePrivateFinalIntInstanceField);
            System.out.println(protectedFinalIntInstanceField);
            System.out.println(publicFinalIntInstanceField);
            System.out.println(privateIntInstanceField);
            System.out.println(packagePrivateIntInstanceField);
            System.out.println(protectedIntInstanceField);
            System.out.println(publicIntInstanceField);
        }
    }


    private class PrivateInner{
        public PrivateInner(){
            System.out.println(privateStaticFinalIntClassField);
            System.out.println(InnerSample.packagePrivateStaticFinalIntClassField);
            System.out.println(InnerSample.protectedStaticFinalIntClassField);
            System.out.println(InnerSample.publicStaticFinalIntClassField);
            System.out.println(InnerSample.privateStaticIntClassField);
            System.out.println(InnerSample.packagePrivateStaticIntClassField);
            System.out.println(InnerSample.protectedStaticIntClassField);
            System.out.println(publicStaticIntClassField);

            System.out.println(privateFinalIntInstanceField);
            System.out.println(packagePrivateFinalIntInstanceField);
            System.out.println(protectedFinalIntInstanceField);
            System.out.println(publicFinalIntInstanceField);
            System.out.println(privateIntInstanceField);
            System.out.println(packagePrivateIntInstanceField);
            System.out.println(protectedIntInstanceField);
            System.out.println(publicIntInstanceField);
        }
    }

}
