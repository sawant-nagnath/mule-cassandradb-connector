package org.mule.modules.cassandradb.internal.metadata;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.model.MetadataType;
import org.mule.modules.cassandradb.internal.config.CassandraConfig;
import org.mule.modules.cassandradb.internal.connection.CassandraConnection;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.InputTypeResolver;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;
import org.mule.runtime.api.metadata.resolving.QueryEntityResolver;
import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;
import java.util.Set;

import static org.mule.metadata.java.api.JavaTypeLoader.JAVA;

public class CassandraMetadataResolver implements QueryEntityResolver, TypeKeysResolver, InputTypeResolver<String>, OutputTypeResolver<String> {

    @Override
    public Set<MetadataKey> getKeys(MetadataContext context) throws MetadataResolvingException, ConnectionException {
        return new MetadataRetriever(CassandraConfig.class.cast(context.getConfig().get()), CassandraConnection.class.cast(context.getConnection().get())).getMetadataKeys();
    }

    @Override
    public MetadataType getInputMetadata(MetadataContext context, String key) throws MetadataResolvingException, ConnectionException {
        return new MetadataRetriever(CassandraConfig.class.cast(context.getConfig().get()), CassandraConnection.class.cast(context.getConnection().get())).getMetadata(key);
    }

    @Override
    public MetadataType getOutputType(MetadataContext metadataContext, String s) throws MetadataResolvingException, ConnectionException {
        BaseTypeBuilder builder = new BaseTypeBuilder(JAVA);
        return builder.anyType().build();
    }

    @Override
    public Set<MetadataKey> getEntityKeys(MetadataContext context) throws MetadataResolvingException, ConnectionException {
        return getKeys(context);
    }

    @Override
    public MetadataType getEntityMetadata(MetadataContext context, String key) throws MetadataResolvingException, ConnectionException {
        return getInputMetadata(context, key);
    }

    @Override
    public String getResolverName() {
        return this.getClass().getName();
    }

    @Override
    public String getCategoryName() {
        return this.getClass().getName();
    }
}