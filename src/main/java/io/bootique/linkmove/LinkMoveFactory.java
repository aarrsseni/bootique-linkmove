package io.bootique.linkmove;

import com.nhl.link.move.connect.StreamConnector;
import com.nhl.link.move.runtime.LmRuntime;
import com.nhl.link.move.runtime.LmRuntimeBuilder;
import com.nhl.link.move.runtime.connect.IConnectorFactory;
import com.nhl.link.move.runtime.connect.URIConnectorFactory;
import com.nhl.link.move.runtime.jdbc.JdbcConnector;
import io.bootique.jdbc.DataSourceFactory;
import io.bootique.linkmove.connector.DataSourceConnectorFactory;
import org.apache.cayenne.configuration.server.ServerRuntime;

import java.util.Objects;

public class LinkMoveFactory {

	private String extractorsDir;

	public LinkMoveFactory() {
		this.extractorsDir = ".";
	}

	public LmRuntime createLinkMove(DataSourceFactory dataSourceFactory, ServerRuntime targetRuntime) {

		IConnectorFactory<JdbcConnector> jdbcCF = createJdbcConnectorFactory(dataSourceFactory);
		Objects.requireNonNull(extractorsDir);

		return new LmRuntimeBuilder().withTargetRuntime(targetRuntime).extractorModelsRoot(extractorsDir)
				.withConnectorFactory(JdbcConnector.class, jdbcCF)
				.withConnectorFactory(StreamConnector.class, URIConnectorFactory.class).build();
	}

	protected IConnectorFactory<JdbcConnector> createJdbcConnectorFactory(DataSourceFactory dataSourceFactory) {
		return new DataSourceConnectorFactory(dataSourceFactory);
	}

	public void setExtractorsDir(String extractorsDir) {
		this.extractorsDir = extractorsDir;
	}
}
