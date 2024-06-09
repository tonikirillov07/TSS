package com.ds.tss;

import com.ds.tss.database.tablesConstants.Clients;
import com.ds.tss.records.ClientRecord;

public class Test {
    public static void main(String[] args) {
        System.out.println(ClientRecord.findClientRecordWithParameter(Clients.NAME_ROW, "Каdfdлак 2290"));
    }
}
