/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cassandradb.automation.functional.metadata;

import com.datastax.driver.core.ProtocolOptions;
import com.datastax.driver.core.ProtocolVersion;
import org.mule.modules.cassandradb.CassandraDBConnector;
import org.mule.modules.cassandradb.api.CassandraClient;
import org.mule.modules.cassandradb.automation.functional.TestDataBuilder;
import org.mule.modules.cassandradb.configurations.AdvancedConnectionParameters;
import org.mule.modules.cassandradb.configurations.ConnectionParameters;
import org.mule.modules.cassandradb.metadata.CreateKeyspaceInput;
import org.mule.modules.cassandradb.automation.util.TestsConstants;
import org.mule.modules.cassandradb.automation.util.PropertiesLoaderUtil;
import org.mule.modules.cassandradb.utils.CassandraConfig;
import org.mule.modules.cassandradb.utils.CassandraDBException;
import org.jetbrains.annotations.NotNull;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mule.api.ConnectionException;
import org.mule.tools.devkit.ctf.exceptions.ConfigurationLoadingFailedException;
import org.mule.tools.devkit.ctf.junit.AbstractMetaDataTestCase;

import java.util.List;

public abstract class CassandraAbstractMetaDataTestCases extends AbstractMetaDataTestCase<CassandraDBConnector> {

    public CassandraAbstractMetaDataTestCases(@NotNull List<String> metadataIds, @NotNull Class<?> categoryClass, Class<CassandraDBConnector> connectorClass) {
        super(metadataIds, categoryClass, connectorClass);
    }

    private static CassandraClient cassClient;
    private static CassandraConfig cassConfig;
    private static AdvancedConnectionParameters advancedConnectionParameters;

    @BeforeClass
    public static void initialSetup() throws ConfigurationLoadingFailedException, ConnectionException, CassandraDBException, InterruptedException {
        cassConfig = getClientConfig();

        advancedConnectionParameters = new AdvancedConnectionParameters(ProtocolVersion.V3, TestsConstants.CLUSTER_NAME, cassConfig.getClusterNodes(), TestsConstants.MAX_WAIT, ProtocolOptions.Compression.NONE, false);

        //get instance of cass client based on the configs
        cassClient = CassandraClient.buildCassandraClient(new ConnectionParameters(cassConfig.getHost(), cassConfig.getPort(), null, null, null, advancedConnectionParameters));
        assert cassClient != null;
        //setup db env
        CreateKeyspaceInput keyspaceInput = new CreateKeyspaceInput();
        keyspaceInput.setKeyspaceName(cassConfig.getKeyspace());
        cassClient.createKeyspace(keyspaceInput);
        cassClient.createTable(TestDataBuilder.getBasicCreateTableInput(TestDataBuilder.getPrimaryKey(), cassConfig.getKeyspace(), TestsConstants.TABLE_NAME_2));
        //required delay to make sure the setup is ok
        Thread.sleep(5000);
    }

    @AfterClass
    public static void tearDown() {
        cassClient.dropTable(TestsConstants.TABLE_NAME_2, cassConfig.getKeyspace());
        cassClient.dropKeyspace(cassConfig.getKeyspace());
    }

    public static CassandraConfig getClientConfig() throws ConfigurationLoadingFailedException {
        //load required properties
        CassandraConfig cassConfig = PropertiesLoaderUtil.resolveCassandraConnectionProps();
        assert cassConfig != null;
        return cassConfig;
    }
}