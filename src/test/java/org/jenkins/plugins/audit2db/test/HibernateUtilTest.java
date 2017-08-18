/**
 *
 */
package org.jenkins.plugins.audit2db.test;

import java.io.IOException;

import junit.framework.Assert;

import org.jenkins.plugins.audit2db.internal.data.HibernateUtil;
import org.junit.Test;

/**
 * @author Marco Scata
 *
 */
public class HibernateUtilTest {
    @Test
    public void checkDdlGeneration() throws IOException {
	final String ddl = HibernateUtil.getSchemaDdl(
		"com.mysql.jdbc.Driver",
		"jdbc:mysql://localhost:3306/mac",
		"mac", "mac");
	Assert.assertNotNull("Unexpected null DDL string", ddl);
	Assert.assertFalse("Unexpected empty DDL string", ddl.isEmpty());
    }
}
