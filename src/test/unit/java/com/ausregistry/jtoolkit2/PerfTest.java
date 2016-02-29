package com.ausregistry.jtoolkit2;

import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.session.*;

public class PerfTest implements Runnable {
    private static final String PROPS_FILE = "/home2/anthony/etc/toolkit/perftest.props";
    private SessionManager sessionManager;
    private Command[] commands;
    private int count;

    public PerfTest() {
    }

    public PerfTest(String[] args) {
        count = Integer.parseInt(args[0]);
        commands = new Command[count];

        for (int i = 0; i < commands.length; i++) {
            if (i < count / 3) {
                commands[i] = new Command(StandardCommandType.CHECK) {
                    private static final long serialVersionUID = -216980404383587901L;

                    protected String toXMLImpl() {
                        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><check><domain:check xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>optometrists.com.au</domain:name><domain:name>prescriptions.com.au</domain:name><domain:name>lingeriemodels.com.au</domain:name><domain:name>intentions.com.au</domain:name><domain:name>cherub.com.au</domain:name><domain:name>medications.com.au</domain:name><domain:name>smashed.com.au</domain:name><domain:name>hunters.com.au</domain:name><domain:name>anthology.com.au</domain:name><domain:name>drenches.com.au</domain:name><domain:name>inks.com.au</domain:name><domain:name>fashiondesigner.com.au</domain:name><domain:name>devices.com.au</domain:name><domain:name>pizzas.com.au</domain:name><domain:name>rogue.com.au</domain:name><domain:name>hairsalon.com.au</domain:name><domain:name>cleanliving.com.au</domain:name><domain:name>requisitions.com.au</domain:name><domain:name>airporttransfers.com.au</domain:name><domain:name>buildingsocieties.com.au</domain:name><domain:name>waiter.com.au</domain:name><domain:name>surnames.com.au</domain:name><domain:name>ambulances.com.au</domain:name></domain:check></check><clTRID>9379431169103313674689062</clTRID></command></epp>";
                    }
                };
            } else if (i < count * 2 / 3) {
                commands[i] = new Command(StandardCommandType.CHECK) {
                    /**
                     *
                     */
                    private static final long serialVersionUID = -8054202272360872140L;

                    protected String toXMLImpl() {
                        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><check><domain:check xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>optometrists.com.au</domain:name><domain:name>prescriptions.com.au</domain:name><domain:name>lingeriemodels.com.au</domain:name><domain:name>intentions.com.au</domain:name><domain:name>cherub.com.au</domain:name><domain:name>medications.com.au</domain:name><domain:name>smashed.com.au</domain:name><domain:name>hunters.com.au</domain:name><domain:name>anthology.com.au</domain:name><domain:name>drenches.com.au</domain:name><domain:name>inks.com.au</domain:name><domain:name>fashiondesigner.com.au</domain:name><domain:name>devices.com.au</domain:name><domain:name>pizzas.com.au</domain:name><domain:name>rogue.com.au</domain:name><domain:name>hairsalon.com.au</domain:name><domain:name>cleanliving.com.au</domain:name><domain:name>requisitions.com.au</domain:name><domain:name>airporttransfers.com.au</domain:name><domain:name>buildingsocieties.com.au</domain:name><domain:name>waiter.com.au</domain:name><domain:name>surnames.com.au</domain:name><domain:name>ambulances.com.au</domain:name></domain:check></check><clTRID>1207098411691033136503880218</clTRID></command></epp>";
                    }
                };
            } else {
                commands[i] = new Command(StandardCommandType.CHECK) {
                    /**
                     *
                     */
                    private static final long serialVersionUID = 3851052297698192792L;

                    protected String toXMLImpl() {
                        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><check><domain:check xmlns:domain=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><domain:name>spoil.com.au</domain:name><domain:name>comics.com.au</domain:name><domain:name>vacuumcleaners.com.au</domain:name><domain:name>cleanwater.com.au</domain:name><domain:name>vacuumcleaner.com.au</domain:name><domain:name>officeequipment.com.au</domain:name><domain:name>golfvacation.com.au</domain:name><domain:name>perfectmatch.com.au</domain:name><domain:name>animalshelter.com.au</domain:name><domain:name>trustees.com.au</domain:name><domain:name>coffeecup.com.au</domain:name><domain:name>memento.com.au</domain:name><domain:name>coincidence.com.au</domain:name><domain:name>atv.com.au</domain:name><domain:name>essay.com.au</domain:name><domain:name>economy.com.au</domain:name><domain:name>realestatedeveloper.com.au</domain:name><domain:name>videogames.com.au</domain:name><domain:name>calendar.com.au</domain:name><domain:name>lovepoem.com.au</domain:name><domain:name>clothes.com.au</domain:name><domain:name>lowgidiet.com.au</domain:name><domain:name>pizzas.com.au</domain:name><domain:name>cateringservices.com.au</domain:name></domain:check></check><clTRID>1152455611691034111578254964</clTRID></command></epp>";
                    }
                };
            }
        }

        try {
            sessionManager = SessionManagerFactory.newInstance(PROPS_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            sessionManager.startup();

            Transaction[] tx = new Transaction[count];

            for (int i = 0; i < tx.length; i++) {
                tx[i] = new Transaction(commands[i], new DomainCheckResponse());
            }

            long start = System.nanoTime();
            sessionManager.execute(tx);
            long end = System.nanoTime();
            long diff = end - start;
            System.out.println(diff);

            sessionManager.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[50];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new PerfTest(args));
            threads[i].start();
        }
    }
}

