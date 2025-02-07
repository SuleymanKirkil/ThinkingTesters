package com.thinkingtesters.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactData {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public static ContactDataBuilder builder() {
        return new ContactDataBuilder();
    }

    public static class ContactDataBuilder {
        private String firstName;
        private String lastName;
        private String email;
        private String phone = "+90";
        private String address;
        private String city;
        private String state;
        private String postalCode;
        private String country = "Turkey";

        ContactDataBuilder() {
        }

        public ContactDataBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ContactDataBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ContactDataBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ContactDataBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public ContactDataBuilder address(String address) {
            this.address = address;
            return this;
        }

        public ContactDataBuilder city(String city) {
            this.city = city;
            return this;
        }

        public ContactDataBuilder state(String state) {
            this.state = state;
            return this;
        }

        public ContactDataBuilder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public ContactDataBuilder country(String country) {
            this.country = country;
            return this;
        }

        public ContactDataBuilder fullName(String fullName) {
            String[] names = fullName.split(" ");
            this.firstName = names[0];
            this.lastName = names.length > 1 ? names[1] : "";
            return this;
        }

        public ContactData build() {
            ContactData contactData = new ContactData();
            contactData.firstName = this.firstName;
            contactData.lastName = this.lastName;
            contactData.email = this.email;
            contactData.phone = this.phone;
            contactData.address = this.address;
            contactData.city = this.city;
            contactData.state = this.state;
            contactData.postalCode = this.postalCode;
            contactData.country = this.country;
            return contactData;
        }
    }
}
