package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;

import java.util.LinkedHashMap;
import java.util.Map;

public class DeathNoteImplementation implements DeathNote {

    private Map<String, Death> note;
    private String lastName;

    public DeathNoteImplementation() {
        note = new LinkedHashMap<>();
        lastName = null;
    }

    @Override
    public String getRule(int ruleNumber) {
        if(ruleNumber < 1 || ruleNumber > RULES.size()){
            throw new IllegalArgumentException("Rule number " + ruleNumber + " does not exist!");
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(String name) {
        if(isNameWritten(name) || name.isEmpty()) {
            throw new NullPointerException("The name can't be null!"); 
        } else {
            note.put(name, new Death());
            lastName = name;
        }
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(lastName.isEmpty() || cause == null) {
            throw new IllegalStateException("No names in DeathNote or " + cause + " is null!");
        }
        return note.get(lastName).setCause(cause);
    }

    @Override
    public boolean writeDetails(String details) {
        if(!isNameWritten(lastName) || details == null) {
            throw new IllegalStateException("No names in DeathNote or " + details + " is null!");
        }
        return note.get(lastName).setDetails(details);
    }

    @Override
    public String getDeathCause(String name) {
      if(!isNameWritten(name)) {
        throw new IllegalArgumentException(name + "is not on the DeathNote!");
      }
      return note.get(name).getCause();
    }

    @Override
    public String getDeathDetails(String name) {
        if(!isNameWritten(name)) {
            throw new IllegalArgumentException(name + "is not on the DeathNote!");
        }
        return note.get(name).getDetails();
    }

    @Override
    public boolean isNameWritten(String name) {
        return note.containsKey(name);
    }

    private static final class Death {
        private static final String DEF_CAUSE = "heart attack";
        private static final int TIME_CAUSE = 40;
        private static final int TIME_DETAILS = 6040;

        private String cause;
        private String details;
        private Long currentTime;

        Death() {
            this.cause = DEF_CAUSE;
            this.details = "";
            this.currentTime = System.currentTimeMillis();
        }
        
        private boolean setCause(String cause) {
            if(System.currentTimeMillis() < this.currentTime + TIME_CAUSE) {
                this.cause = cause;
                return true;
            }
            return false;
        }

        private boolean setDetails(String details) {
            if(System.currentTimeMillis() < this.currentTime + TIME_DETAILS) {
                this.details = details;
                return true;
            }
            return false;
        }

        private String getCause() {
            return this.cause;
        }

        private String getDetails() {
            return this.details;
        }

    }

}