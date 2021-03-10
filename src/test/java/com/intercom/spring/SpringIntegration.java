package com.intercom.spring;

import com.intercom.IntercomDistanceCalc;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;

@RunWith(SpringRunner.class)
@Rollback
@SpringBootTest(classes = IntercomDistanceCalc.class)
@ActiveProfiles("test")
public abstract class SpringIntegration {

    @AfterTransaction
    public void clean() {
        // = Clean dirty stuffs after transaction
    }
}