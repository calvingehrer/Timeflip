package at.qe.sepm.skeleton.ui.beans;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CompleteMethodBeanTest {

    @Autowired
    CompleteMethodBean completeMethodBean;

    @Test
    public void completeTask() {
        List<String> list = completeMethodBean.completeTask("IMPLEMENTIERUNG");
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void completeInterval() {
        List<String> list = completeMethodBean.completeInterval("daily");
        Assert.assertFalse(list.isEmpty());
    }
}