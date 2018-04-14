package com.s14222.tau;

import com.s14222.tau.dbdemo.service.UndeadManagerImpl;
import com.s14222.tau.domain.Undead;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import com.s14222.tau.repository.UndeadRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class UndeadMockedTest {
    UndeadRepository undeadRepository;
    @Mock
    Connection connectionMock;

    @Mock
    UndeadRepository undeadRepositoryMock;
    @Mock
    PreparedStatement insertStatementMock;
    @Mock
    PreparedStatement deleteStatementMock;

    @Mock
    PreparedStatement selectStatementMock;
    @Mock
    PreparedStatement selectByIdStatementMock;
    @Mock
    PreparedStatement updateStatementMock;

    @Before
    public void setupDatabase() throws SQLException {

        when(connectionMock.prepareStatement("INSERT INTO Undeads (nazwa, zycie, sila, level) VALUES ( ?, ?, ?, ?)"))
                .thenReturn(insertStatementMock);
        when(connectionMock.prepareStatement("DELETE FROM Undeads WHERE id = ?"))
                .thenReturn(deleteStatementMock);
        when(connectionMock.prepareStatement("SELECT * FROM Undeads"))
                .thenReturn(selectStatementMock);
        when(connectionMock.prepareStatement("SELECT * FROM undeads WHERE id = ?"))
                .thenReturn(selectByIdStatementMock);
        when(connectionMock.prepareStatement("UPDATE Undeads SET nazwa = ?, zycie = ?, sila = ?, level = ? WHERE id = ?"))
                .thenReturn(updateStatementMock);
        undeadRepository = new UndeadManagerImpl();
        undeadRepositoryMock = mock(UndeadManagerImpl.class);
        undeadRepository.setConnection(connectionMock);
        verify(connectionMock).prepareStatement("INSERT INTO Undeads (nazwa, zycie, sila, level) VALUES ( ?, ?, ?, ?)");
        verify(connectionMock).prepareStatement("SELECT * FROM Undeads");
        verify(connectionMock).prepareStatement("DELETE FROM Undeads WHERE id = ?");
        verify(connectionMock).prepareStatement("SELECT * FROM undeads WHERE id = ?");
        verify(connectionMock).prepareStatement("UPDATE Undeads SET nazwa = ?, zycie = ?, sila = ?, level = ? WHERE id = ?");
    }

    @Test
    public void TestDodania() throws SQLException {
        when(insertStatementMock.executeUpdate()).thenReturn(1);
        Undead undead1 = new Undead(0, "Trups", 1, 100, 100);
        assertEquals(1, undeadRepository.add(undead1));

        verify(insertStatementMock, times(1)).setString(1, "Trups");
        verify(insertStatementMock, times(1)).setInt(2, 100);
        verify(insertStatementMock, times(1)).setInt(3, 100);
        verify(insertStatementMock, times(1)).setInt(4, 1);
        verify(insertStatementMock).executeUpdate();
    }

    @Test(expected = IllegalStateException.class)
    public void TestDodaniaZNullem() throws SQLException {
        when(insertStatementMock.executeUpdate()).thenThrow(new SQLException());
        Undead undead1 = new Undead(0, null, 1, 100, 100);
        assertEquals(1, undeadRepository.add(undead1));

    }

    @Test
    public void TestUsuniecia() throws SQLException {
        when(deleteStatementMock.executeUpdate()).thenReturn(1);
        assertEquals(1, undeadRepository.deleteById(6));
    }

    abstract class AbstractResultSet implements ResultSet {
        int i = 0;

        @Override
        public int getInt(String s) throws SQLException {
            return 1;
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            return "Zombie";
        }

        @Override
        public boolean next() throws SQLException {
            if (i == 1)
                return false;
            i++;
            return true;
        }
    }

    abstract class AbstractResultSetAll implements ResultSet {
        int i = 0;

        @Override
        public int getInt(String s) throws SQLException {
            return 1;
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            return "Zombie";
        }

        @Override
        public boolean next() throws SQLException {
            if (i == 1)
                return false;
            i++;
            return true;
        }
    }

    @Test
    public void TestAll() throws SQLException {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getInt("id")).thenCallRealMethod();
        when(mockedResultSet.getString("nazwa")).thenCallRealMethod();
        when(mockedResultSet.getInt("level")).thenCallRealMethod();
        when(mockedResultSet.getInt("zycie")).thenCallRealMethod();
        when(mockedResultSet.getInt("sila")).thenCallRealMethod();
        when(selectStatementMock.executeQuery()).thenReturn(mockedResultSet);

        assertEquals(1, undeadRepository.getAll().size());

        verify(selectStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getInt("id");
        verify(mockedResultSet, times(1)).getString("nazwa");
        verify(mockedResultSet, times(1)).getInt("level");
        verify(mockedResultSet, times(1)).getInt("zycie");
        verify(mockedResultSet, times(1)).getInt("sila");
        verify(mockedResultSet, times(2)).next();
    }

    @Test
    public void TestById() throws SQLException {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getInt("id")).thenCallRealMethod();
        when(mockedResultSet.getString("nazwa")).thenCallRealMethod();
        when(mockedResultSet.getInt("level")).thenCallRealMethod();
        when(mockedResultSet.getInt("zycie")).thenCallRealMethod();
        when(mockedResultSet.getInt("sila")).thenCallRealMethod();
        when(selectByIdStatementMock.executeQuery()).thenReturn(mockedResultSet);

        assertNotNull(undeadRepository.getById(1));

        verify(selectByIdStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getInt("id");
        verify(mockedResultSet, times(1)).getString("nazwa");
        verify(mockedResultSet, times(1)).getInt("level");
        verify(mockedResultSet, times(1)).getInt("zycie");
        verify(mockedResultSet, times(1)).getInt("sila");
        verify(mockedResultSet, times(2)).next();
    }

    @Test
    public void TestUpdate() throws SQLException {
        Undead undead1 = new Undead(1, "Trups", 1, 100, 100);
        doReturn(undead1).when(undeadRepositoryMock).getById(isA(Integer.class));
        Undead undeadToUpdate = undeadRepositoryMock.getById(2);
        undeadToUpdate.setNazwa("Zombie");
        undeadRepository.updateById(undeadToUpdate);

        Undead undead2 = new Undead(1, "Zombie", 1, 100, 100);
        doReturn(undead2).when(undeadRepositoryMock).getById(2);

        assertThat(undeadRepositoryMock.getById(2).getNazwa(), is(undeadToUpdate.getNazwa()));
        assertEquals(undeadRepositoryMock.getById(2).getNazwa(), undeadToUpdate.getNazwa());
        verify(updateStatementMock, times(1)).setString(1, "Zombie");
        verify(updateStatementMock, times(1)).setInt(2, 100);
        verify(updateStatementMock, times(1)).setInt(3, 100);
        verify(updateStatementMock, times(1)).setInt(4, 1);
        verify(updateStatementMock).executeUpdate();
    }
}
