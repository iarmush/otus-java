package ru.otus.crm.service.db;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.Client;
import ru.otus.crm.repository.ClientRepository;

@Service
public class DbServiceClientImpl implements DBServiceClient {

    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    public DbServiceClientImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = clientRepository.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }


    @Override
    public List<Client> findAll() {
        return transactionManager.doInTransaction(() -> {
            var clients = new ArrayList<Client>();
            var clientIterable = clientRepository.findAll();
            clientIterable.iterator().forEachRemaining(clients::add);

            log.info("clientList: {}", clients);
            return clients;
        });
    }
}
