package com.mulesoft.mule.cassandradb.automation.functional;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.mulesoft.mule.cassandradb.api.CassandraClient;
import com.mulesoft.mule.cassandradb.utils.CassandraConfig;
import com.mulesoft.mule.cassandradb.utils.Constants;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mulesoft.mule.cassandradb.utils.CassandraDBException;
import org.mule.api.ConnectionException;
import org.mule.tools.devkit.ctf.exceptions.ConfigurationLoadingFailedException;

import static org.hamcrest.Matchers.*;

public class SelectTestCases extends BaseTestCases {

    private static CassandraClient cassClient;
    private static CassandraConfig cassConfig;

    @BeforeClass
    public static void setup() throws ConnectionException, CassandraDBException, IOException, ConfigurationLoadingFailedException {
        cassConfig = getClientConfig();
        cassClient = configureClient(cassConfig);
        cassClient.insert(cassConfig.getKeyspace(), Constants.TABLE_NAME, TestDataBuilder.getValidEntity());
    }

    @AfterClass
    public static void tearDown() throws CassandraDBException, ConnectionException {
        cassClient.dropTable(Constants.TABLE_NAME, cassConfig.getKeyspace());
    }

    @Test
    public void testSelectNativeQueryWithParameters() throws CassandraDBException {
        List<Map<String, Object>> result = getConnector().select(TestDataBuilder.VALID_PARAMETERIZED_QUERY, TestDataBuilder.getValidParmList());
        Assert.assertThat(Integer.valueOf(result.size()),greaterThan(0));
    }
    
    @Test(expected=CassandraDBException.class)
    public void testSelectNativeQueryWithInvalidParameters() throws CassandraDBException {
        List<Map<String, Object>> result = getConnector().select(TestDataBuilder.VALID_PARAMETERIZED_QUERY, new LinkedList<>());
        Assert.assertThat(Integer.valueOf(result.size()),greaterThan(0));
    }
    
    @Test
    public void testSelectDSQLQuery() throws CassandraDBException {
        List<Map<String, Object>> result = getConnector().select(TestDataBuilder.VALID_DSQL_QUERY, null);
        Assert.assertThat(Integer.valueOf(result.size()),greaterThan(0));
    }

}
