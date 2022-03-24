import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Session;
import server.SessionContainer;
import server.SessionController;
import server.api.TestActivityRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SessionContollerTest {

    private TestActivityRepository repo;

    @BeforeEach
    public void setup() {
        repo = new TestActivityRepository();
        for(int i=0; i<60; i++) {
            Activity toBeAdded = new Activity("test"+i, "test","10", 10L       ,"test");
            repo.save(toBeAdded);
        }
        SessionContainer.clear();
    }

    @Test
    public void getActivePlayersEmptyTest() {
        SessionController sess = new SessionController(repo);
        assertEquals(0, sess.getActivePlayers());
    }

    @Test
    public void get60RandomActivitiesLessThan60Test() {
        TestActivityRepository emptyRepo = new TestActivityRepository();
        for(int i=0; i<59; i++) {
            Activity toBeAdded = new Activity("test"+i, "test","10", 10L       ,"test");
            emptyRepo.save(toBeAdded);
        }
        SessionController sess = new SessionController(emptyRepo);
        assertNull(sess.get60RandomActivities());
    }

    @Test
    public void getActivePlayersTest() {
        SessionController sess = new SessionController(repo);
        int singlePlayerSessCount = 55;
        for(int i=0; i<singlePlayerSessCount; i++) {
            String username = "test"+i;
            SessionContainer.createSession(false,username,sess.get60RandomActivities());
        }
        assertEquals(singlePlayerSessCount, sess.getActivePlayers());
    }

    @Test
    public void getPlayersInSessionTest(){
        SessionController sess = new SessionController(repo);
        SessionContainer.createSession(true,"test",sess.get60RandomActivities());
        List<String> list = new ArrayList<String>();
        list.add("test");
        assertEquals(list,sess.getPlayersInSession("test"));
        int sessionId = SessionContainer.findUserSession("test");
        Session session = SessionContainer.getSession(sessionId);
        session.addPlayer("test1");
        session.addPlayer("test2");
        session.addPlayer("test3");
        list.add("test1");
        list.add("test2");
        list.add("test3");
        assertEquals(list,sess.getPlayersInSession("test"));
        session.removePlayer("test2");
        assertNotEquals(list,sess.getPlayersInSession("test"));
    }

    @Test
    public void getPlayersInSessionNotFoundTest() {
        SessionController sess = new SessionController(repo);
        List<String> list = new ArrayList<String>();
        list.add("test");
        assertEquals(list,sess.getPlayersInSession("test"));
    }

    @Test
    public void getCurrentQuestionTest() {
        SessionController sess = new SessionController(repo);
        Date date = new Date();
        assertNotEquals(Session.emptyQ,sess.getCurrentQuestion("test", false));
    }

    @Test
    public void submitAnswerOutOfGameTest() {
        SessionController sess = new SessionController(repo);
        assertFalse(sess.submitAnswer("test",0,0));
    }

    @Test
    public void submitAnswerInvalidAnswerFormatTest() {
        SessionController sess = new SessionController(repo);
        SessionContainer.createSession(false,"test",sess.get60RandomActivities());
        Session x = (Session)SessionContainer.getSession(SessionContainer.findUserSession("test"));
        x.startGame();
        x.getCurrentQuestion();
        assertFalse(sess.submitAnswer("test",4,0));
    }

    @Test
    public void submitAnswerNotStartedTest() {
        SessionController sess = new SessionController(repo);
        SessionContainer.createSession(false,"test",sess.get60RandomActivities());
        assertFalse(sess.submitAnswer("test",2,0));
    }

    @Test
    public void submitAnswerNotCurrentQuestionTest() {
        SessionController sess = new SessionController(repo);
        SessionContainer.createSession(false,"test",sess.get60RandomActivities());
        Session x = (Session)SessionContainer.getSession(SessionContainer.findUserSession("test"));
        x.startGame();
        x.getCurrentQuestion();
        assertFalse(sess.submitAnswer("test",2,1));
    }

    @Test
    public void submitAnswerTest() {
        SessionController sess = new SessionController(repo);
        SessionContainer.createSession(false,"test",sess.get60RandomActivities());
        Session x = (Session)SessionContainer.getSession(SessionContainer.findUserSession("test"));
        x.startGame();
        x.getCurrentQuestion();
        assertTrue(sess.submitAnswer("test",2,0));
    }

    @Test
    public void submitAnswerTimedOutTest() {
        SessionController sess = new SessionController(repo);
        SessionContainer.createSession(false,"test",sess.get60RandomActivities());
        Session x = (Session)SessionContainer.getSession(SessionContainer.findUserSession("test"));
        x.startGame();
        x.getCurrentQuestion();
        x.setQuestionStartedAt(Long.valueOf(0));
        assertFalse(sess.submitAnswer("test",2,0));
    }

    @Test
    public void addJokerNoSessionTest() {
        SessionController sess = new SessionController(repo);
        assertFalse(sess.addJoker("test",0,0));
    }

    @Test
    public void addJokerNoStartedTest() {
        SessionController sess = new SessionController(repo);
        SessionContainer.createSession(false,"test",sess.get60RandomActivities());
        Session x = (Session)SessionContainer.getSession(SessionContainer.findUserSession("test"));
        assertFalse(sess.addJoker("test",0,-1));
    }

    @Test
    public void addJokerWrongQuestionNumTest() {
        SessionController sess = new SessionController(repo);
        SessionContainer.createSession(false,"test",sess.get60RandomActivities());
        Session x = (Session)SessionContainer.getSession(SessionContainer.findUserSession("test"));
        x.startGame();
        x.getCurrentQuestion();
        assertFalse(sess.addJoker("test",0,5));
    }


    @Test
    public void addJokerTest() {
        SessionController sess = new SessionController(repo);
        SessionContainer.createSession(false,"test",sess.get60RandomActivities());
        Session x = (Session)SessionContainer.getSession(SessionContainer.findUserSession("test"));
        x.startGame();
        x.getCurrentQuestion();
        assertTrue(sess.addJoker("test",0,0));
    }
}
