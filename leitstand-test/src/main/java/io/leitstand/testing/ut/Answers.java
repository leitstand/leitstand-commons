package io.leitstand.testing.ut;

import javax.transaction.RollbackException;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class Answers {
    
    @SuppressWarnings("rawtypes")
    public static final Answer ROLLBACK = new Answer() {

        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            throw new RollbackException();
        }
        
    };
    
}
