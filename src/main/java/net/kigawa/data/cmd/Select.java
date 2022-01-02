package net.kigawa.data.cmd;

import net.kigawa.StringUtil;
import net.kigawa.data.sql.AbstractSqlCmd;

import java.util.Collections;
import java.util.LinkedList;

import static net.kigawa.data.sql.Sql.*;

public class Select extends AbstractSqlCmd<Select> {
    private final String table;
    private LinkedList<String> columnList;
    private boolean distinct = false;

    public Select(String table) {
        this.table = table;
    }

    public Select distinct() {
        distinct = true;
        return this;
    }

    public Select columns(String... columns) {
        Collections.addAll(columnList, columns);
        return this;
    }

    public Select create() {
        cmd.clear();
        cmd.add(SELECT);

        if (distinct) cmd.add(DISTINCT);

        if (columnList == null) cmd.add("*");
        else {
            cmd.add(StringUtil.insertSymbol(",", columnList));
        }

        cmd.add(FROM);
        cmd.add(table);

        return this;
    }

    public Where<Select> where() {
        create();
        return new Where<>(this, cmd);
    }
}
