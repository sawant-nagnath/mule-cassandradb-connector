/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cassandradb.automation.functional;


import org.junit.After;
import org.junit.Test;
import org.mule.modules.cassandradb.api.CreateTableInput;
import org.mule.modules.cassandradb.automation.util.TestsConstants;

import static org.junit.Assert.assertTrue;


public class CreateTableTestCases extends AbstractTestCases {
    @Test
    public void testCreateTableWithSuccess() throws Exception {
        CreateTableInput basicCreateTableInput = TestDataBuilder.getBasicCreateTableInput(TestDataBuilder.getColumns(), getCassandraProperties().getKeyspace(), TestsConstants.TABLE_NAME_1);
        assertTrue(createTable(basicCreateTableInput));
    }

    @Test
    public void testCreateTableWithCompositePKWithSuccess() throws Exception {
        CreateTableInput basicCreateTableInput = TestDataBuilder.getBasicCreateTableInput(TestDataBuilder.getCompositePrimaryKey(), getCassandraProperties().getKeyspace(), TestsConstants.TABLE_NAME_2);
        assertTrue(createTable(basicCreateTableInput));
    }
}