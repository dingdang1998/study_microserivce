package com.labi.shardingdao.config.sharding;

import com.baomidou.mybatisplus.core.parser.AbstractJsqlParser;
import com.macro.cloud.entity.sharding.ShardingTable;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.values.ValuesStatement;

import java.util.*;

/**
 * @program: study_microservice
 * @description: sharding分表字段检查
 * @author: dzp
 * @create: 2021-12-17 16:06
 **/
@Slf4j
public class ShardingColumnChecker extends AbstractJsqlParser {

    private final Map<String, String> sharingMap;

    public ShardingColumnChecker(List<ShardingTable> shardingTable) {
        sharingMap = new HashMap<>(shardingTable.size());
        shardingTable.forEach(s -> sharingMap.put(s.getLogicTable(), s.getShardingColumn()));
    }

    @Override
    public void processInsert(Insert insert) {
        final Table table = insert.getTable();
        if (sharingMap.get(table.getName()) != null) {
            final String shardingColumn = sharingMap.get(table.getName());
            boolean exist = false;
            for (Column column : insert.getColumns()) {
                if (column.getColumnName().toLowerCase().equals(shardingColumn.toLowerCase())) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                throw new RuntimeException("分表字段不能为空！" + table.getName() + "." + shardingColumn + "\nsql : " + insert.toString());

            }
        }
    }

    @Override
    public void processDelete(Delete delete) {
        final Table table = delete.getTable();
        if (sharingMap.get(table.getName()) != null) {
            this.checkTable(table.getName(), delete.getWhere(), delete.toString());
        }
    }

    @Override
    public void processUpdate(Update update) {
        final Table table = update.getTable();

        if (sharingMap.get(table.getName()) != null) {
            this.checkTable(table.getName(), update.getWhere(), update.toString());
        }

    }

    @Override
    public void processSelectBody(SelectBody selectBody) {
        selectBody.accept(new SelectVisitor() {
            @Override
            public void visit(PlainSelect plainSelect) {
                final List<String> tables = getTables(plainSelect.getFromItem());
                tables.forEach(s -> {
                    if (sharingMap.get(s) != null) {
                        checkTable(s, plainSelect.getWhere(), plainSelect.toString());
                    }
                });
            }

            @Override
            public void visit(SetOperationList setOperationList) {

            }

            @Override
            public void visit(WithItem withItem) {

            }

            @Override
            public void visit(ValuesStatement valuesStatement) {

            }
        });
    }

    /**
     * 检查该表字段，前提是已确认该表为sharding分表
     *
     * @param tableName 表名
     * @param where     where
     * @param sql       原sql
     */
    private void checkTable(String tableName, Expression where, String sql) {
        if (where == null) {
            throw new RuntimeException("分表字段不能为空！" + tableName + "." + sharingMap.get(tableName) + "\nsql : " + sql);
        }
        final String shardingColumn = sharingMap.get(tableName);
        final List<String> columns = getColumns(where);
        if (!columns.contains(shardingColumn.toLowerCase())) {
            throw new RuntimeException("分表字段不能为空！" + tableName + "." + shardingColumn + "\nsql : " + sql);
        }
    }

    /**
     * 获取sql where 中的所有字段名
     *
     * @param where
     * @return
     */
    private List<String> getColumns(Expression where) {
        if (where == null) {
            return Collections.emptyList();
        }
        List<String> columnList = new ArrayList<>();
        where.accept(new ExpressionVisitorAdapter() {
            @Override
            public void visit(Column tableColumn) {
                columnList.add(tableColumn.getColumnName().toLowerCase());
            }
        });
        return columnList;
    }

    /**
     * 获取本条sql中的所有表名
     *
     * @param fromItem
     * @return
     */
    private List<String> getTables(FromItem fromItem) {
        if (fromItem == null) {
            return Collections.emptyList();
        }
        List<String> tableList = new ArrayList<>();
        fromItem.accept(new FromItemVisitorAdapter() {
            @Override
            public void visit(Table table) {
                tableList.add(table.getName().toLowerCase());
            }
        });
        return tableList;
    }
}
