--liquibase formatted sql
--changeset seed:20220413151102

/*
 $2a$06$lvO4A2I1vW0qwBAh5jQFfu9XZ0CiNrMVMHIc5GCbMZspLRReI4JrO: test1234!@#$TEST
 $2a$06$feYcPZxFzsABAwg4U2pOEeFJzvJw5kGI0U6Hzj3TEh6UdmkQ1vLW2: xptmxm1234!@#$TEST
 */
BEGIN;
INSERT INTO `order_bcs_orders` (`id`, `user_id`, `code`, `product_name`, `ordered_at`, `created_at`, `updated_at`, `deleted_at`) VALUES
                                                                                                                                     (X'0F98A866D3AE4BD289C0C7A30B54F226', X'8FDCECBEAECC4FA48E031EABCD8BC796', '50E46F6E49AC', '상품2', '2022-03-01 23:59:59', '2022-03-01 23:59:59', '2022-03-01 23:59:59', NULL),
                                                                                                                                     (X'1D4B63BF11B54089A1A4B3C1E387ECD1', X'D2DC165B89F04251925D801D685A286A', '35603FC60920', '상품4', '2022-03-02 00:10:00', '2022-03-02 00:10:00', '2022-03-02 00:10:00', NULL),
                                                                                                                                     (X'25BBDD6C731C4C56A6C288671F48A01D', X'D2DC165B89F04251925D801D685A286A', 'AD8AB1162360', '상품5', '2022-03-02 23:59:59', '2022-03-02 23:59:59', '2022-03-02 23:59:59', NULL),
                                                                                                                                     (X'912793A579D146B08F5937251185611A', X'0A09D078A47A4D02B80C03F1B32B6A50', '8DBE3C4F992C', '상품3', '2022-03-01 23:59:59', '2022-03-01 23:59:59', '2022-03-01 23:59:59', NULL),
                                                                                                                                     (X'AD2C578C323E44AF8D363C640F6CF950', X'D2DC165B89F04251925D801D685A286A', '9670A02E92BC', '상품1', '2022-03-01 23:59:59', '2022-03-01 23:59:59', '2022-03-01 23:59:59', NULL),
                                                                                                                                     (X'B239682162194C6E9BB08D245826ABF5', X'D2DC165B89F04251925D801D685A286A', '99EF26717D3C', '상품2', '2022-03-01 23:59:59', '2022-03-01 23:59:59', '2022-03-01 23:59:59', NULL),
                                                                                                                                     (X'DCDFBF9342A94BCDA02CB2CAC2298554', X'D2DC165B89F04251925D801D685A286A', '83B63C1D8293', '상품1', '2022-03-01 23:59:59', '2022-03-01 23:59:59', '2022-03-01 23:59:59', NULL);

INSERT INTO `user_bcs_user_information` (`id`, `sex`, `created_at`, `updated_at`) VALUES
    (X'F1BD248B19F23254525A901D685A286A', 'MALE', '2022-01-01 00:01:00', '2022-01-01 00:01:00');

INSERT INTO `user_bcs_user_orders` (`id`, `order_id`, `code`, `product_name`, `ordered_at`, `created_at`, `updated_at`) VALUES
                                                                                                                            (X'0A09D078A47A4D02B80C03F1B32B6A50', X'912793A579D146B08F5937251185611A', '8DBE3C4F992C', '상품3', '2022-03-01 23:59:59', '2022-03-01 23:59:59', '2022-03-01 23:59:59'),
                                                                                                                            (X'8FDCECBEAECC4FA48E031EABCD8BC796', X'0F98A866D3AE4BD289C0C7A30B54F226', '50E46F6E49AC', '상품2', '2022-03-01 23:59:59', '2022-03-01 23:59:59', '2022-03-01 23:59:59'),
                                                                                                                            (X'D2DC165B89F04251925D801D685A286A', X'25BBDD6C731C4C56A6C288671F48A01D', 'AD8AB1162360', '상품5', '2022-03-02 23:59:59', '2022-03-03 00:00:00', '2022-03-03 00:00:00');

INSERT INTO `user_bcs_users` (`id`, `name`, `nick_name`, `email`, `phone_number`, `password`, `information_id`, `order_id`, `created_at`, `updated_at`, `deleted_at`) VALUES
                                                                                                                                                                          (X'0A09D078A47A4D02B80C03F1B32B6A50', '삭제당함4', 'e', 'test3@gmail.com', '+821042345678', '$2a$06$lvO4A2I1vW0qwBAh5jQFfu9XZ0CiNrMVMHIc5GCbMZspLRReI4JrO', NULL, X'0A09D078A47A4D02B80C03F1B32B6A50', '2022-01-01 00:00:00', '2022-01-04 03:00:00', '2022-02-03 12:59:59'),
                                                                                                                                                                          (X'4A6D44BAC2E54E95BD462B334ECE91C4', '삭제당함6', 'g', 'test5@gmail.com', '+821062345678', '$2a$06$lvO4A2I1vW0qwBAh5jQFfu9XZ0CiNrMVMHIc5GCbMZspLRReI4JrO', NULL, NULL, '2022-01-01 00:00:00', '2022-01-06 05:00:00', '2022-02-03 12:59:59'),
                                                                                                                                                                          (X'8FDCECBEAECC4FA48E031EABCD8BC796', '김이박', 'b', 'test_real1@gmail.com', '+821012345678', '$2a$06$lvO4A2I1vW0qwBAh5jQFfu9XZ0CiNrMVMHIc5GCbMZspLRReI4JrO', NULL, X'8FDCECBEAECC4FA48E031EABCD8BC796', '2022-01-01 00:00:00', '2022-01-01 00:00:00', NULL),
                                                                                                                                                                          (X'94B70F109B5A487696284CDE9AADFDBE', '삭제당함2', 'c', 'test1@gmail.com', '+821022345678', '$2a$06$lvO4A2I1vW0qwBAh5jQFfu9XZ0CiNrMVMHIc5GCbMZspLRReI4JrO', NULL, NULL, '2022-01-01 00:00:00', '2022-01-02 01:00:00', '2022-02-03 12:59:59'),
                                                                                                                                                                          (X'C7E091CBC483471AAE449BE6A917D9F3', '삭제당함3', 'd', 'test2@gmail.com', '+821032345678', '$2a$06$lvO4A2I1vW0qwBAh5jQFfu9XZ0CiNrMVMHIc5GCbMZspLRReI4JrO', NULL, NULL, '2022-01-01 00:00:00', '2022-01-03 02:00:00', '2022-02-03 12:59:59'),
                                                                                                                                                                          (X'D2DC165B89F04251925D801D685A286A', '홍길동', 'a', 'test@gmail.com', '+821012345678', '$2a$06$lvO4A2I1vW0qwBAh5jQFfu9XZ0CiNrMVMHIc5GCbMZspLRReI4JrO', X'F1BD248B19F23254525A901D685A286A', X'D2DC165B89F04251925D801D685A286A', '2022-01-01 00:00:00', '2022-01-01 00:00:00', NULL),
                                                                                                                                                                          (X'DAFCA41C2B2F41418B9D301E4E754AAD', '삭제당함5', 'f', 'test4@gmail.com', '+821052345678', '$2a$06$lvO4A2I1vW0qwBAh5jQFfu9XZ0CiNrMVMHIc5GCbMZspLRReI4JrO', NULL, NULL, '2022-01-01 00:00:00', '2022-01-05 04:00:00', '2022-02-03 12:59:59');

COMMIT;
