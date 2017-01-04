package pl.beutysite.recruit.event;

public class SeriousEnterpriseEventBusLookup {
    public static final SeriousEnterpriseEventBus seeb=new SeriousEnterpriseEventBus() {
        @Override
        public void sendEvent(String event) {
        }
    };

}
