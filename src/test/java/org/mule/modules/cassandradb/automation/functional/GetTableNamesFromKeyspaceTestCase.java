/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cassandradb.automation.functional;

import org.junit.Test;
import org.mule.modules.cassandradb.api.CreateKeyspaceInput;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mule.modules.cassandradb.automation.functional.TestDataBuilder.KEYSPACE_NAME_3;
import static org.mule.modules.cassandradb.automation.functional.TestDataBuilder.TABLE_NAME_1;
import static org.mule.modules.cassandradb.automation.functional.TestDataBuilder.TABLE_NAME_2;
import static org.mule.modules.cassandradb.automation.functional.TestDataBuilder.getBasicCreateTableInput;
import static org.mule.modules.cassandradb.automation.functional.TestDataBuilder.getColumns;

public class GetTableNamesFromKeyspaceTestCase extends AbstractTestCases {

    @Test
    public void testTableNamesFromLoggedInKeyspace() throws Exception {
        createTable(getBasicCreateTableInput(getColumns(), testKeyspace, TABLE_NAME_1));
        createTable(getBasicCreateTableInput(getColumns(), testKeyspace, TABLE_NAME_2));
        List<String> tableNames = getTableNamesFromKeyspace(testKeyspace);
        assertEquals(2, tableNames.size());
        assertTrue(tableNames.contains(TABLE_NAME_1));
        assertTrue(tableNames.contains(TABLE_NAME_2));
        dropTable(TABLE_NAME_1, testKeyspace);
        dropTable(TABLE_NAME_2, testKeyspace);
    }

    @Test
    public void testTableNamesFromCustomInKeyspace() throws Exception {
        CreateKeyspaceInput keyspaceInput = new CreateKeyspaceInput();
        keyspaceInput.setKeyspaceName(KEYSPACE_NAME_3);
        createKeyspace(keyspaceInput);
        createTable(getBasicCreateTableInput(getColumns(), KEYSPACE_NAME_3, TABLE_NAME_1));
        createTable(getBasicCreateTableInput(getColumns(), KEYSPACE_NAME_3, TABLE_NAME_2));
        createTable(getBasicCreateTableInput(getColumns(), testKeyspace, "testTable"));
        List<String> tableNames = getTableNamesFromKeyspace(KEYSPACE_NAME_3);
        assertEquals(2, tableNames.size());
        assertTrue(tableNames.contains(TABLE_NAME_1));
        assertTrue(tableNames.contains(TABLE_NAME_2));
        dropKeyspace(KEYSPACE_NAME_3);
    }
}
