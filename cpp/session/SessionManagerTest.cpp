#include "se/PollRequestCommand.hpp"
#include "se/PollResponse.hpp"
#include "session/Transaction.hpp"
#include "session/SessionManagerFactory.hpp"
#include "session/SessionManager.hpp"
#include "session/Timer.hpp"
#include "session/TestEnvironment.hpp"

#include "common/init.hpp"
#include "common/Test.hpp"

#include <pthread.h>

#include <memory>

#include <vector>

using namespace std;

static TestEnvironment props;

// static void testExecuteParallelTransactions() throw (EPPException);

void doWork()
{
    init("etc/toolkit2.conf");

    string op("shutdown");
    {
        auto_ptr<SessionManager> manager(SessionManagerFactory::newInstance(&props));
        manager->startup();
        string oldPW = props.getClientPW();
        string newPW = "23wejfd3wD*Sk*#";
        manager->changePassword(oldPW, newPW);
        manager->changePassword(newPW, oldPW);
        manager->shutdown();
    }

    {
        auto_ptr<SessionManager> manager(SessionManagerFactory::newInstance(&props));
        manager->startup();
        try
        {
            PollRequestCommand cmd;
            PollResponse resp;
            Transaction *tx = new Transaction(&cmd, &resp);

            manager->shutdown();
            op = "execute";

            manager->execute(*tx);
            FAIL("Execute should fail after shutdown");
        }
        catch (EPPException& e)
        {
            // Could fail for lots of reasons, but we want one in particular.
            ASSERT(
                    e.getMessage().find(
                        "Can not execute a command because the session manager is not started.")
                    != string::npos);
        }
        catch (...)
        {
            FAIL("Unexpected exception of unknown type occurred in " + op);
        }
    }

    try {
        Timer::setTime("20070101.010101");
        op = "newInstance";
        auto_ptr<SessionManager> manager(SessionManagerFactory::newInstance(&props));
        op = "startup";
        manager->startup();

        op = "run (keep-alive)";
        // Spawn the keep alive thread.
        manager->run();
        sleep(10);
        manager->shutdown();
    }
    catch (EPPException& e)
    {
        throw e;
    }
    catch (...)
    {
        FAIL("Unexpected exception of unknown type occurred in " + op);
    }

    //testExecuteParallelTransactions();
}

SessionManager *sessionManager;

typedef std::vector<Transaction> TransactionVector;

void *threadFunc(void *data)
{
    TransactionVector *txVptr = (TransactionVector *) data;
    TransactionVector& txV = *txVptr;
    try
    {
        sessionManager->execute(txV);
    }
    catch (EPPException& ex)
    {
        FAIL("Execution of transactions failed: " + ex.getMessage());
    }

    return NULL;
}

#if 0
void testExecuteParallelTransactions() throw (EPPException)
{
    const int PARALLEL_DEGREE = 10;
    const int SEQUENTIAL_COUNT = 1;

    pthread_t threads[PARALLEL_DEGREE];

    TransactionVector txV[PARALLEL_DEGREE];

    int i, j;

    for (i = 0; i < PARALLEL_DEGREE; i++)
    {
        for (j = 0; j < SEQUENTIAL_COUNT; j++)
        {
            PollRequestCommand *cmd = new PollRequestCommand();
            Response *response = new Response();
            //PollRequestCommand& cmdRef = *cmd;
            //Response& responseRef = *response;
            //Transaction *tx = new Transaction(cmdRef, responseRef);
            Transaction *tx = new Transaction(cmd, response);
            std::string xml = tx->getCommand()->toXML();
            Transaction& txRef = *tx;
            txV[i].push_back(txRef);
            Transaction& txX = txV[i][j];
            Command *cmdX = txX.getCommand();
            std::string xmlX = cmdX->toXML();
        }
    }

    sessionManager = SessionManagerFactory::newInstance(&props);

    try
    {
        sessionManager->startup();

        const pthread_attr_t *ATTR = NULL;

        for (i = 0; i < PARALLEL_DEGREE; i++)
            pthread_create(&(threads[i]), ATTR, threadFunc, &(txV[i]));

        for (i = 0; i < PARALLEL_DEGREE; i++)
            pthread_join(threads[i], NULL);

        sessionManager->shutdown();
    }
    catch (EPPException& e)
    {
        throw e;
    }
}
#endif /* 0 */

int main(int argc, char* argv[])
{
    TEST_run(doWork);
    return TEST_errorCount();
}

