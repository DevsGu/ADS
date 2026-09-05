-- ============================================================
-- RetroMarket - Seed inicial para PostgreSQL
-- Branch analisada: dev
--
-- Ordem de dependências:
--   1. tabelas de domínio
--   2. usuários
--   3. plataformas
--   4. categorias
--   5. anúncios
--   6. fotos
--   7. favoritos
--
-- Compatível com PostgreSQL.
-- ============================================================

BEGIN;

-- ============================================================
-- 1. TABELAS DE DOMÍNIO
-- ============================================================

INSERT INTO condicao (codigo, descricao)
VALUES
    ('NOVO',         'Novo (Lacrado)'),
    ('SEMINOVO',     'Seminovo (Excelente)'),
    ('USADO',        'Usado (Bom estado)'),
    ('RECONDICIONADO','Recondicionado'),
    ('DEFEITO',      'Com Defeito / Sucata')
ON CONFLICT (codigo) DO UPDATE
SET descricao = EXCLUDED.descricao;


INSERT INTO completude (codigo, descricao)
VALUES
    ('COMPLETO',  'Completo (CIB)'),
    ('LOOSE',     'Loose (Avulso)'),
    ('INCOMPLETO','Incompleto / Personalizado')
ON CONFLICT (codigo) DO UPDATE
SET descricao = EXCLUDED.descricao;


INSERT INTO status_anuncio (codigo, descricao)
VALUES
    ('ATIVO', 'Ativo'),
    ('RESERVADO', 'Reservado'),
    ('VENDIDO', 'Vendido'),
    ('INATIVO', 'Inativo')
ON CONFLICT (codigo) DO UPDATE
SET descricao = EXCLUDED.descricao;


-- ============================================================
-- 2. USUÁRIOS
-- ============================================================
-- A aplicação armazena senha em senha_hash.
-- Estes valores são hashes BCrypt de exemplo.
--
-- Senha de teste de todos os usuários:
-- 123456
--
-- Caso a implementação atual ainda não esteja usando BCrypt,
-- substitua pelos valores compatíveis com o seu serviço de
-- autenticação.

INSERT INTO usuario (
    id,
    nome,
    email,
    senha_hash,
    data_cadastro
)
VALUES
    (
        1,
        'Lucas Ferreira',
        'lucas@retromarket.local',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoO5J8R9i7Y0LQ8V1X9BzQ7SxNQmL0P8a2',
        TIMESTAMP '2026-08-01 09:00:00'
    ),
    (
        2,
        'Marina Souza',
        'marina@retromarket.local',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoO5J8R9i7Y0LQ8V1X9BzQ7SxNQmL0P8a2',
        TIMESTAMP '2026-08-02 10:30:00'
    ),
    (
        3,
        'Rafael Gomes',
        'rafael@retromarket.local',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoO5J8R9i7Y0LQ8V1X9BzQ7SxNQmL0P8a2',
        TIMESTAMP '2026-08-03 14:15:00'
    )
ON CONFLICT (id) DO UPDATE
SET
    nome = EXCLUDED.nome,
    email = EXCLUDED.email,
    senha_hash = EXCLUDED.senha_hash,
    data_cadastro = EXCLUDED.data_cadastro;


-- ============================================================
-- 3. PLATAFORMAS
-- ============================================================

INSERT INTO plataforma (
    id,
    nome,
    fabricante,
    geracao
)
VALUES
    (1, 'Super Nintendo', 'Nintendo', 4),
    (2, 'Nintendo 64', 'Nintendo', 5),
    (3, 'Mega Drive', 'Sega', 4),
    (4, 'PlayStation', 'Sony', 5),
    (5, 'PlayStation 2', 'Sony', 6),
    (6, 'Game Boy', 'Nintendo', 4),
    (7, 'Dreamcast', 'Sega', 6),
    (8, 'Atari 2600', 'Atari', 2)
ON CONFLICT (id) DO UPDATE
SET
    nome = EXCLUDED.nome,
    fabricante = EXCLUDED.fabricante,
    geracao = EXCLUDED.geracao;


-- ============================================================
-- 4. CATEGORIAS
-- ============================================================

INSERT INTO categoria (id, nome)
VALUES
    (1, 'Console'),
    (2, 'Jogo'),
    (3, 'Acessório'),
    (4, 'Colecionável'),
    (5, 'Kit / Bundle')
ON CONFLICT (id) DO UPDATE
SET nome = EXCLUDED.nome;


-- ============================================================
-- 5. ANÚNCIOS
-- ============================================================
--
-- Cada anúncio referencia:
--   usuario_id
--   plataforma_id
--   categoria_id
--   condicao_codigo
--   completude_codigo
--   status_codigo
--
-- Os IDs são explícitos para facilitar testes com GET /{id}.
-- ============================================================

INSERT INTO anuncio (
    id,
    usuario_id,
    plataforma_id,
    categoria_id,
    condicao_codigo,
    completude_codigo,
    status_codigo,
    titulo,
    descricao,
    preco,
    localizacao,
    data_publicacao,
    data_atualizacao
)
VALUES
(
    1,
    1,
    2,
    1,
    'USADO',
    'COMPLETO',
    'ATIVO',
    'Nintendo 64 completo com controle',
    'Nintendo 64 em bom estado de conservação, com console, fonte e controle original.',
    899.90,
    'Tauá-CE',
    TIMESTAMP '2026-08-05 09:30:00',
    TIMESTAMP '2026-08-05 09:30:00'
),
(
    2,
    1,
    1,
    2,
    'SEMINOVO',
    'COMPLETO',
    'ATIVO',
    'Super Mario World original para Super Nintendo',
    'Cartucho original, em excelente estado e acompanhado de caixa.',
    349.90,
    'Fortaleza-CE',
    TIMESTAMP '2026-08-05 11:00:00',
    TIMESTAMP '2026-08-05 11:00:00'
),
(
    3,
    2,
    4,
    1,
    'USADO',
    'LOOSE',
    'RESERVADO',
    'PlayStation 1 SCPH-5501',
    'Console funcionando, com cabos e um controle paralelo.',
    529.90,
    'Quixadá-CE',
    TIMESTAMP '2026-08-06 14:20:00',
    TIMESTAMP '2026-08-07 16:10:00'
),
(
    4,
    2,
    5,
    5,
    'USADO',
    'COMPLETO',
    'VENDIDO',
    'PlayStation 2 Slim + 2 controles + jogos',
    'Kit completo com console, dois controles, memory card e cinco jogos.',
    799.90,
    'Sobral-CE',
    TIMESTAMP '2026-08-06 17:45:00',
    TIMESTAMP '2026-08-10 12:30:00'
),
(
    5,
    3,
    3,
    1,
    'RECONDICIONADO',
    'INCOMPLETO',
    'ATIVO',
    'Mega Drive revisado',
    'Console revisado e testado. Carcaça com sinais de uso e controle compatível.',
    449.90,
    'Juazeiro do Norte-CE',
    TIMESTAMP '2026-08-07 08:00:00',
    TIMESTAMP '2026-08-07 08:00:00'
),
(
    6,
    3,
    6,
    2,
    'USADO',
    'LOOSE',
    'ATIVO',
    'The Legend of Zelda: Link''s Awakening',
    'Cartucho original de Game Boy, vendido avulso.',
    279.90,
    'Crato-CE',
    TIMESTAMP '2026-08-07 10:15:00',
    TIMESTAMP '2026-08-07 10:15:00'
),
(
    7,
    1,
    7,
    1,
    'DEFEITO',
    'INCOMPLETO',
    'INATIVO',
    'Dreamcast para peças',
    'Console sem teste, vendido no estado e destinado a retirada de peças.',
    180.00,
    'Tauá-CE',
    TIMESTAMP '2026-08-08 13:00:00',
    TIMESTAMP '2026-08-08 18:20:00'
),
(
    8,
    2,
    8,
    4,
    'SEMINOVO',
    'COMPLETO',
    'ATIVO',
    'Atari 2600 original para coleção',
    'Console preservado, com caixa e acessórios originais.',
    1199.90,
    'Fortaleza-CE',
    TIMESTAMP '2026-08-08 15:30:00',
    TIMESTAMP '2026-08-08 15:30:00'
),
(
    9,
    3,
    2,
    2,
    'NOVO',
    'COMPLETO',
    'ATIVO',
    'Mario Kart 64 lacrado',
    'Unidade lacrada destinada a colecionadores.',
    899.90,
    'Fortaleza-CE',
    TIMESTAMP '2026-08-09 09:40:00',
    TIMESTAMP '2026-08-09 09:40:00'
),
(
    10,
    1,
    1,
    3,
    'SEMINOVO',
    'LOOSE',
    'ATIVO',
    'Controle original de Super Nintendo',
    'Controle original em excelente estado de funcionamento.',
    129.90,
    'Tauá-CE',
    TIMESTAMP '2026-08-09 11:10:00',
    TIMESTAMP '2026-08-09 11:10:00'
)
ON CONFLICT (id) DO UPDATE
SET
    usuario_id = EXCLUDED.usuario_id,
    plataforma_id = EXCLUDED.plataforma_id,
    categoria_id = EXCLUDED.categoria_id,
    condicao_codigo = EXCLUDED.condicao_codigo,
    completude_codigo = EXCLUDED.completude_codigo,
    status_codigo = EXCLUDED.status_codigo,
    titulo = EXCLUDED.titulo,
    descricao = EXCLUDED.descricao,
    preco = EXCLUDED.preco,
    localizacao = EXCLUDED.localizacao,
    data_publicacao = EXCLUDED.data_publicacao,
    data_atualizacao = EXCLUDED.data_atualizacao;

-- ============================================================
-- 6. FOTOS
-- ============================================================

INSERT INTO foto_anuncio (
    id,
    anuncio_id,
    url,
    ordem,
    principal
)
VALUES
    (
        1,
        1,
        'https://images.unsplash.com/photo-1607853202273-797f1c22a38e',
        1,
        TRUE
    ),
    (
        2,
        1,
        'https://images.unsplash.com/photo-1592840062661-7f1b6d6d5a94',
        2,
        FALSE
    ),
    (
        3,
        2,
        'https://images.unsplash.com/photo-1542751371-adc38448a05e',
        1,
        TRUE
    ),
    (
        4,
        3,
        'https://images.unsplash.com/photo-1600080972464-8e5c8b0b11b8',
        1,
        TRUE
    ),
    (
        5,
        4,
        'https://images.unsplash.com/photo-1606144042614-b2417e99c4e3',
        1,
        TRUE
    ),
    (
        6,
        5,
        'https://images.unsplash.com/photo-1592840062661-7f1b6d6d5a94',
        1,
        TRUE
    ),
    (
        7,
        6,
        'https://images.unsplash.com/photo-1550745165-9bc0b252726f',
        1,
        TRUE
    ),
    (
        8,
        8,
        'https://images.unsplash.com/photo-1603481546238-487240415921',
        1,
        TRUE
    ),
    (
        9,
        9,
        'https://images.unsplash.com/photo-1585079542156-2755d9c8a094',
        1,
        TRUE
    ),
    (
        10,
        10,
        'https://images.unsplash.com/photo-1592840062661-7f1b6d6d5a94',
        1,
        TRUE
    )
ON CONFLICT (id) DO UPDATE
SET
    anuncio_id = EXCLUDED.anuncio_id,
    url = EXCLUDED.url,
    ordem = EXCLUDED.ordem,
    principal = EXCLUDED.principal;


-- ============================================================
-- 7. FAVORITOS
-- ============================================================
-- A combinação usuario_id + anuncio_id é UNIQUE.
-- ============================================================

INSERT INTO favorito (
    id,
    usuario_id,
    anuncio_id,
    data_favorito
)
VALUES
    (
        1,
        1,
        2,
        TIMESTAMP '2026-08-08 09:15:00'
    ),
    (
        2,
        1,
        8,
        TIMESTAMP '2026-08-08 09:20:00'
    ),
    (
        3,
        2,
        1,
        TIMESTAMP '2026-08-08 10:30:00'
    ),
    (
        4,
        2,
        9,
        TIMESTAMP '2026-08-09 13:00:00'
    ),
    (
        5,
        3,
        1,
        TIMESTAMP '2026-08-09 14:45:00'
    )
ON CONFLICT (id) DO UPDATE
SET
    usuario_id = EXCLUDED.usuario_id,
    anuncio_id = EXCLUDED.anuncio_id,
    data_favorito = EXCLUDED.data_favorito;


-- ============================================================
-- 8. SINCRONIZAÇÃO DAS SEQUENCES
-- ============================================================
-- Como os IDs foram definidos manualmente, ajustamos as
-- sequences do PostgreSQL para que os próximos INSERTs
-- automáticos não tentem reutilizar IDs já existentes.
-- ============================================================

SELECT setval(
    pg_get_serial_sequence('usuario', 'id'),
    GREATEST(COALESCE((SELECT MAX(id) FROM usuario), 1), 1),
    true
);

SELECT setval(
    pg_get_serial_sequence('plataforma', 'id'),
    GREATEST(COALESCE((SELECT MAX(id) FROM plataforma), 1), 1),
    true
);

SELECT setval(
    pg_get_serial_sequence('categoria', 'id'),
    GREATEST(COALESCE((SELECT MAX(id) FROM categoria), 1), 1),
    true
);

SELECT setval(
    pg_get_serial_sequence('anuncio', 'id'),
    GREATEST(COALESCE((SELECT MAX(id) FROM anuncio), 1), 1),
    true
);

SELECT setval(
    pg_get_serial_sequence('foto_anuncio', 'id'),
    GREATEST(COALESCE((SELECT MAX(id) FROM foto_anuncio), 1), 1),
    true
);

SELECT setval(
    pg_get_serial_sequence('favorito', 'id'),
    GREATEST(COALESCE((SELECT MAX(id) FROM favorito), 1), 1),
    true
);

COMMIT;