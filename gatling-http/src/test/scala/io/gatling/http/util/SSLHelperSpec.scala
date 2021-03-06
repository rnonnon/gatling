/**
 * Copyright 2011-2014 eBusiness Information, Groupe Excilys (www.ebusinessinformation.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gatling.http.util

import java.io.{ FileNotFoundException, File }

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification

/**
 * @author Ivan Mushketyk
 */
@RunWith(classOf[JUnitRunner])
class SSLHelperSpec extends Specification {

  val KEYSTORE = "testkeystore"
  val PASSWORD = "123456"

  val classLoader = this.getClass.getClassLoader

  def fileFromResource(classPathResource: String): String = {
    new File(classLoader.getResource(classPathResource).getFile).getAbsolutePath
  }

  "SSLHelperSpec" should {
    "load keystore from file" in {
      val keystoreFile = fileFromResource(KEYSTORE)

      val keyManagers = SSLHelper.newKeyManagers(None, keystoreFile, PASSWORD, None)
      keyManagers must have size 1
    }

    "load keystore from classpath" in {
      val keyManagers = SSLHelper.newKeyManagers(None, KEYSTORE, PASSWORD, None)
      keyManagers must have size 1
    }

    "throw FileNotFoundException when load non-existing keystore from classpath" in {
      SSLHelper.newKeyManagers(None, "some/non/existing", PASSWORD, None) must throwA[FileNotFoundException]
    }

    "load truststore from file" in {
      val truststoreFile = fileFromResource(KEYSTORE)

      val trustManagers = SSLHelper.newTrustManagers(None, truststoreFile, PASSWORD, None)
      trustManagers must have size 1
    }

    "load truststore from classpath" in {
      val trustManagers = SSLHelper.newTrustManagers(None, KEYSTORE, PASSWORD, None)
      trustManagers must have size 1
    }

    "throw FileNotFoundException when load non-existing truststore from classpath" in {
      SSLHelper.newTrustManagers(None, "some/non/existing", PASSWORD, None) must throwA[FileNotFoundException]
    }
  }
}
