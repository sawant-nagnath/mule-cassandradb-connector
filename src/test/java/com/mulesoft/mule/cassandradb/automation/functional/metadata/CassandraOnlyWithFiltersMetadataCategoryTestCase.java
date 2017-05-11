/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.mule.cassandradb.automation.functional.metadata;

import com.mulesoft.mule.cassandradb.CassandraDBConnector;
import com.mulesoft.mule.cassandradb.automation.functional.TestDataBuilder;
import com.mulesoft.mule.cassandradb.metadata.CassandraOnlyWithFiltersMetadataCategory;

public class CassandraOnlyWithFiltersMetadataCategoryTestCase extends AbstractCassMetaDataTestCase {

    public CassandraOnlyWithFiltersMetadataCategoryTestCase() {
        super(TestDataBuilder.cassandraCategoryMetadataTestKeys, CassandraOnlyWithFiltersMetadataCategory.class, CassandraDBConnector.class);
    }
}
