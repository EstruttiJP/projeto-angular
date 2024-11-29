CREATE TABLE IF NOT EXISTS `carrinho` (
  `id_user` bigint NOT NULL,
  `id_produto` bigint NOT NULL,
  PRIMARY KEY (`id_user`, `id_produto`),
  FOREIGN KEY (`id_user`) REFERENCES `users` (`id`),
  FOREIGN KEY (`id_produto`) REFERENCES `produtos` (`id`)
) ENGINE=InnoDB;