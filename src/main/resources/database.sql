CREATE DATABASE bank_db;

CREATE TABLE account (
                         id VARCHAR(255) PRIMARY KEY,
                         account_type VARCHAR(50) NOT NULL,
                         CONSTRAINT chk_account_type CHECK (account_type IN ('STANDARD', 'PREMIUM', 'GOLD'))
);

CREATE TABLE transaction (
                             id VARCHAR(255) PRIMARY KEY,
                             created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             transaction_type VARCHAR(10) NOT NULL,
                             amount DECIMAL(15, 2) NOT NULL,
                             reason VARCHAR(255),
                             account_id VARCHAR(255) NOT NULL,
                             CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
                             CONSTRAINT chk_transaction_type CHECK (transaction_type IN ('IN', 'OUT')),
                             CONSTRAINT chk_amount_positive CHECK (amount > 0)
);

CREATE INDEX idx_transaction_account_id ON transaction(account_id);
CREATE INDEX idx_transaction_type ON transaction(transaction_type);
CREATE INDEX idx_transaction_created_at ON transaction(created_at);


DELETE FROM transaction;
DELETE FROM account;

INSERT INTO account (id, account_type) VALUES
                                           ('ACC-1001', 'STANDARD'),
                                           ('ACC-1002', 'PREMIUM'),
                                           ('ACC-1003', 'GOLD'),
                                           ('ACC-1004', 'STANDARD');

INSERT INTO transaction (id, created_at, transaction_type, amount, reason, account_id) VALUES
                                                                                           ('TX-001', '2026-08-01 09:30:00+00', 'IN', 1500.00, 'Virement Salaire d''août', 'ACC-1001'),
                                                                                           ('TX-002', '2026-08-03 14:15:00+00', 'OUT', 200.00, 'Retrait distributeur automatique', 'ACC-1001'),
                                                                                           ('TX-003', '2026-08-05 18:45:00+00', 'OUT', 50.00, 'Courses au supermarché', 'ACC-1001'),
                                                                                           ('TX-004', '2026-08-02 11:00:00+00', 'IN', 5000.00, 'Dépôt initial', 'ACC-1002'),
                                                                                           ('TX-005', '2026-08-10 16:20:00+00', 'IN', 1200.00, 'Remboursement frais de mission', 'ACC-1002'),
                                                                                           ('TX-006', '2026-08-12 10:00:00+00', 'OUT', 850.00, 'Achat nouvel ordinateur portable', 'ACC-1002'),
                                                                                           ('TX-007', '2026-08-01 08:00:00+00', 'IN', 15000.00, 'Transfert épargne d''investissement', 'ACC-1003'),
                                                                                           ('TX-008', '2026-08-15 19:30:00+00', 'OUT', 3200.00, 'Réservation vacances d''été', 'ACC-1003'),
                                                                                           ('TX-009', '2026-08-18 12:00:00+00', 'IN', 300.00, 'Virement de bienvenue', 'ACC-1004'),
                                                                                           ('TX-010', '2026-08-19 15:10:00+00', 'OUT', 150.00, 'Paiement facture d''électricité', 'ACC-1004');
Rédiger

