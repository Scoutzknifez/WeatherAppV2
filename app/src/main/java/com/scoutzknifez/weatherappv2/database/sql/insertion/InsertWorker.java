package com.scoutzknifez.weatherappv2.database.sql.insertion;

import com.scoutzknifez.weatherappv2.database.sql.SQLHelper;
import com.scoutzknifez.weatherappv2.database.sql.Table;
import com.scoutzknifez.weatherappv2.database.sql.Worker;
import com.scoutzknifez.weatherappv2.utility.Utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsertWorker extends Worker {
    private String objectStringForm;

    public InsertWorker(Table table, Databasable databasableObject) {
        super(table);
        setObjectStringForm(SQLHelper.databasableToInsertionForm(databasableObject));
    }

    @Override
    public void run() {
        if (getStatement() == null)
            return;

        doInsertion();
        closeStatement();
    }

    private void doInsertion() {
        String sqlArg = "INSERT INTO " + getTable().name() + " VALUES " + getObjectStringForm();
        try {
            getStatement().execute(sqlArg);
        } catch (Exception e) {
            Utils.log("Failed to do insertion on table: " + getTable().name());
        }
    }
}
