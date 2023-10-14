package io.curiositycore.curiouscustodian.model.lookup.types;

import io.curiositycore.curiouscustodian.model.lookup.AbstractLookup;

public class LogLookup extends AbstractLookup {
    protected String logType;
    public static class LogLookupBuilder extends AbstractLookup.AbstractLookupBuilder<LogLookup> {
        private String logType;

        @Override
        public LogLookup build() {
            LogLookup logLookup = super.build();
            logLookup.logType = this.logType;
            return logLookup;
        }

        @Override
        protected LogLookup createLookup() {
            return new LogLookup();
        }
        public LogLookupBuilder setLogType(String logType){
            this.logType = logType;
            return this;
        }
    }

}
