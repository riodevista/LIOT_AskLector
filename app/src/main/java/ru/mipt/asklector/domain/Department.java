package ru.mipt.asklector.domain;

/**
 * Created by Dmitry Bochkov on 21.10.2015.
 */
public class Department {

        private GUID guid;
        private String name;
        private String description;

        public Department(GUID guid, String name, String description) {
            this.guid = guid;
            this.name = name;
            this.description = description;
        }

        public GUID getGuid() {
            return guid;
        }

        public void setGuid(GUID guid) {
            this.guid = guid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

}
