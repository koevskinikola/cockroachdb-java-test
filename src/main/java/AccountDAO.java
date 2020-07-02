/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class AccountDAO {

  private final DataSource ds;
  private Connection connection;

  public AccountDAO(DataSource ds) {
    this.ds = ds;
  }

  public void beginTransaction() {
    try {
      connection = ds.getConnection();

      connection.setAutoCommit(false);

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public int getBalance(String id) {
    try {
      PreparedStatement prs = connection.prepareStatement("SELECT balance FROM accounts WHERE id = " + id + ";");

      try(ResultSet rs = prs.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1);
        } else {
          throw new RuntimeException("no data");
        }
      }
    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }
  }
  
  public String getTxTimestamp() {
    try {
      PreparedStatement prs = connection.prepareStatement("select cluster_logical_timestamp()");

      try(ResultSet rs = prs.executeQuery()) {
        if (rs.next()) {
          return rs.getString(1);
        } else {
          throw new RuntimeException("no data");
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void updateBalance(String id, int amount) {
    String sAmount = Integer.toString(amount);
    try {
      PreparedStatement prs = connection.prepareStatement("UPDATE accounts SET balance = " + sAmount + " WHERE id = " + id + ";");

      prs.execute();

      prs.close();
    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }
  }

  public void commitTransaction() {
    try {
      connection.commit();
      connection.close();
    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }
  }
}