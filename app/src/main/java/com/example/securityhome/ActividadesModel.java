package com.example.securityhome;

public class ActividadesModel {

        private String Id;
        private String Partial_Id;
        private String name_activity;
        private String description;
        private String ready;
        private String grade;

        public ActividadesModel(String id, String partial_Id, String name_activity, String description, String ready, String grade) {
                Id = id;
                Partial_Id = partial_Id;
                this.name_activity = name_activity;
                this.description = description;
                this.ready = ready;
                this.grade = grade;
        }

        public String getId() {
                return Id;
        }

        public void setId(String id) {
                Id = id;
        }

        public String getPartial_Id() {
                return Partial_Id;
        }

        public void setPartial_Id(String partial_Id) {
                Partial_Id = partial_Id;
        }

        public String getName_activity() {
                return name_activity;
        }

        public void setName_activity(String name_activity) {
                this.name_activity = name_activity;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getReady() {
                return ready;
        }

        public void setReady(String ready) {
                this.ready = ready;
        }

        public String getGrade() {
                return grade;
        }

        public void setGrade(String grade) {
                this.grade = grade;
        }
}
