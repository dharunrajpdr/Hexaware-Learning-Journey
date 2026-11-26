
package com.crm.model;

import com.crm.interfaces.Auditable;

public class ImportedCustomer implements Auditable {

    private final String source;

    public ImportedCustomer(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
