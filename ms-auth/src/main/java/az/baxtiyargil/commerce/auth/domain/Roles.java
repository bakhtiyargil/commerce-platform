package az.baxtiyargil.commerce.auth.domain;

public enum Roles {

    MERCHANT,
    CUSTOMER,
    ADMIN;

    public String asString() {
        return this.name();
    }
}
