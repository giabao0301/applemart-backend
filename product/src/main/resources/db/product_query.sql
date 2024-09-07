-- category
INSERT INTO category
    (id, url_key, name, thumbnail_url)
VALUES
    (1, "mac", "Mac", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725543748/category/store-card-13-mac-nav-202310_tjmsef.png"),
    (2, "iphone", "iPhone", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725543778/category/store-card-13-iphone-nav-202309_eatlkw.png"),
    (3, "ipad", "iPad", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725543803/category/store-card-13-ipad-nav-202210_kpvdvr.png"),
    (4, "watch", "Apple Watch", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725543853/category/store-card-13-watch-nav-202309_GEO_VN_czihll.png"),
    (5, "airpods", "AirPods", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725543876/category/store-card-13-airpods-nav-202209_zgsdwq.png"),
    (6, "phu-kien", "Phụ Kiện", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725543893/category/store-card-13-accessories-nav-202403_lmoh4g.png");

INSERT INTO category
    (id, parent_category_id, url_key, name)
VALUES
    (7, 1, "macbook-air", "MacBook Air"),
    (8, 1, "macbook-pro", "Macbook Pro"),
	(9, 1, "imac", "iMac"),
	(10, 1, "mac-mini", "Mac mini"),
	(11, 1, "mac-studio", "Mac Studio"),
    (12, 1, "studio-display", "Studio Display"),
    (13, 1, "pro-display-xdr", "Pro Display XDR"),
    
    (15, 2, "iphone-15-pro", "iPhone 15 Pro"),
    (16, 2, "iphone-15", "iPhone 15"),
    (17, 2, "iphone-14", "iPhone 14"),
    (18, 2, "iphone-13", "iPhone 13"),
    (19, 2, "iphone-se", "iPhone SE"),
    
    (20, 3, "ipad-pro", "iPad Pro"),
    (21, 3, "ipad-air", "iPad Air"),
    (22, 3, "ipad", "iPad"),
    (23, 3, "ipad-mini", "iPad mini"),
    
    (24, 4, "apple-watch", "Apple Watch"),
    (25, 4, "apple-watch-ultra", "Apple Watch Ultra"),
    (26, 4, "apple-watch-se", "Apple Watch SE"),

	(27, 5, "airpods-2nd-generation", "AirPods (Thế hệ thứ 2)"),
    (28, 5, "airpods-3rd-generation", "AirPods (Thế hệ thứ 3)"),
    (29, 5, "airpods-pro", "AirPods Pro (thế hệ thứ 2)"),
    (30, 5, "airpods-max", "AirPods Max");
     
 -- product variation
INSERT INTO variation
    (id, product_id, name)
VALUES
    (1, 1, "Màu"),
    (2, 1, "RAM"),
    (3, 1, "Ổ cứng"),
    
	(4, 8, "Màu"),
    (5, 8, "RAM"),
    (6, 8, "Ổ cứng"),   
    
    (7, 9, "Màu"),
    (8, 9, "RAM"),
    (9, 9, "Ổ cứng"),
    
    (10, 10, "RAM"),
    (11, 10, "Ổ cứng"),
    
    (12, 11, "RAM"),
    (13, 11, "Ổ cứng"),   
    
	(22, 12, "Màn hình"),
    (22, 12, "Chân đế"),
    
    (22, 13, "Màn hình"),
    
    (14, 15, "Màu"),
    (15, 15, "Dung lượng lưu trữ"), 
    
    (16, 16, "Màu"),
    (17, 16, "Dung lượng lưu trữ"),   
    
    (18, 17, "Màu"),
    (19, 17, "Dung lượng lưu trữ"),  
    
    (20, 18, "Màu"),
    (21, 18, "Dung lượng lưu trữ"),
    
    (22, 19, "Màu"),
    (23, 19, "Dung lượng lưu trữ"),
    
    (24, 20, "Màu"),
    (25, 20, "Dung lượng lưu trữ"),  
    
    (26, 21, "Màu"),
    (27, 21, "Dung lượng lưu trữ"),   

    (28, 22, "Màu"),
    (29, 22, "Dung lượng lưu trữ"),    
    
	(30, 23, "Màu"),
    (31, 23, "Dung lượng lưu trữ"), 
    
    (32, 24, "Màu"),
    (33, 24, "Kích thước mặt"),
    
    (34, 25, "Màu"),
    (35, 25, "Kích thước mặt"),
    
    (36, 26, "Màu"),
    (37, 26, "Kích thước mặt"),
    (38, 30, "Màu");

   
INSERT INTO variation_option
    (id, variation_id, value)
VALUES
    (6, 2, "8GB"),
    (7, 2, "16GB"),
    (8, 3, "256GB"),
    (9, 3, "512GB"),
    (10, 3, "1TB"),
    
    (11, 5, "8GB"),
    (12, 5, "16GB"),
    (13, 6, "512GB"),
    (14, 6, "1TB"),
    
	(15, 8, "8GB"),
    (16, 9, "256GB"),
    (17, 9, "512GB"),

	(11, 10, "8GB"),
    (12, 10, "16GB"),
    (16, 11, "256GB"),
    (17, 11	, "512GB"),

	(11, 12, "32GB"),
    (12, 12, "64GB"),
    (16, 13, "512GB"),
    (17, 13	, "1TB"),		

    (21, 29, "41mm");
 
 
 -- product option
INSERT INTO variation_option
    (id, variation_id, value, image_url)
VALUES
    (1, 1, "Đêm Xanh Thẳm", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725411165/product%20colors/mba13-midnight-select-202402_SW_COLOR_wfutry.jpg"),
    (2, 1, "Xám Không gian", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725411305/product%20colors/mba13-spacegray-select-202402_SW_COLOR_c2phee.jpg"),
    (3, 1, "Ánh Sao", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725411338/product%20colors/mba13-starlight-select-202402_SW_COLOR_clkflo.jpg"),
    (4, 1, "Bạc", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725411379/product%20colors/mba13-silver-select-202402_SW_COLOR_gm0zcy.jpg"),
    
    (5, 4, "Xám Không gian", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725411305/product%20colors/mba13-spacegray-select-202402_SW_COLOR_c2phee.jpg"),
    (6, 4, "Bạc", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725411379/product%20colors/mba13-silver-select-202402_SW_COLOR_gm0zcy.jpg"),
    (7, 4, "Đen", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725415533/product%20colors/mbp-16-spaceblack-cto-hero-202310_SW_COLOR_s6cilz.jpg"),
	
    (6, 7, "Xanh Dương", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725461722/product%20colors/imac-24-blue-selection-hero-202310_SW_COLOR_wocnn2.jpg"),
    (7, 7, "Xanh Lá", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725461784/product%20colors/imac-24-green-selection-hero-202310_SW_COLOR_lrisyk.jpg"),
    (8, 7, "Hồng", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725461821/product%20colors/imac-24-pink-selection-hero-202310_SW_COLOR_bgjizm.jpg"),
    (9, 7, "Bạc", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725461873/product%20colors/imac-24-silver-selection-hero-202310_SW_COLOR_fsbl1f.jpg"),
    (10, 7, "Vàng", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725461902/product%20colors/imac-24-yellow-selection-hero-202310_SW_COLOR_axb65v.jpg"),
    (11, 7, "Cam", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725461942/product%20colors/imac-24-orange-selection-hero-202310_SW_COLOR_hrepzn.jpg"),
    (12, 7, "Tím", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725462052/product%20colors/imac-24-purple-selection-hero-202310_SW_COLOR_pa20fp.jpg"),
    
    (13, 10, "Titan Tự nhiên", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725462186/product%20colors/iphone-pro-finish-naturaltitanium-202309_hzknqj.jpg"),
    (14, 10, "Titan Xanh", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725462241/product%20colors/iphone-pro-finish-bluetitanium-202309_gn23es.jpg"),
    (15, 10, "Titan Trắng", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725462282/product%20colors/iphone-pro-finish-whitetitanium-202309_f51viu.jpg"),
    (16, 10, "Titan Đen", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725462331/product%20colors/iphone-pro-finish-blacktitanium-202309_juhnkw.jpg"),
    
	(17, 12, "Xanh Dương", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725465679/product%20colors/finish-blue-202309_bhfywv.jpg"),
    (18, 12, "Hồng", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725465695/product%20colors/finish-pink-202309_lwgxuf.jpg"),
    (19, 12, "Vàng", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725465731/product%20colors/finish-yellow-202309_urmxf7.jpg"),
    (20, 12, "Xanh Lá", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725465763/product%20colors/finish-green-202309_vmawml.jpg"),
    (21, 12, "Đen", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725465789/product%20colors/finish-black-202309_ntjtod.jpg"),

	(22, 14, "Xanh Dương", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725465679/product%20colors/finish-blue-202309_bhfywv.jpg"),
    (23, 14, "Tím", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466043/product%20colors/finish-purple-202209_xptrat.jpg"),
    (24, 14, "Vàng", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725465731/product%20colors/finish-yellow-202309_urmxf7.jpg"),
    (25, 14, "Đêm Xanh Thẳm", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466047/product%20colors/finish-midnight-202209_ehmp2y.jpg"),
    (26, 14, "Ánh Sao", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466082/product%20colors/finish-starlight-202209_wkfz2q.jpg"),
    (27, 14, "Đỏ", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466227/product%20colors/finish-red-202209_ftcv0j.jpg"),

	(28, 16, "Xanh Dương", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466386/product%20colors/iphone-13-finish-blue-202108_i0e7ox.jpg"),
    (29, 16, "Hồng", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725465695/product%20colors/finish-pink-202309_lwgxuf.jpg"),
    (30, 16, "Đêm Xanh Thẳm", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466047/product%20colors/finish-midnight-202209_ehmp2y.jpg"),
    (31, 16, "Ánh Sao", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466082/product%20colors/finish-starlight-202209_wkfz2q.jpg"),
    (32, 16, "Xanh Lá", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466541/product%20colors/iphone-13-finish-green-202203_db0wt0.jpg"),
    (33, 16, "Đỏ", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466227/product%20colors/finish-red-202209_ftcv0j.jpg"),

	(34, 18, "Đêm Xanh Thẳm", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466047/product%20colors/finish-midnight-202209_ehmp2y.jpg"),
    (35, 18, "Ánh Sao", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466082/product%20colors/finish-starlight-202209_wkfz2q.jpg"),
    (36, 18, "Đỏ", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466227/product%20colors/finish-red-202209_ftcv0j.jpg"),
    
    (37, 20, "Bạc", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466777/product%20colors/ipad-pro-finish-silver-2024_hktabf.png"),
    (38, 20, "Đen Không Gian", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725466872/product%20colors/ipad-pro-finish-space-black-2024_vajjye.png"),
    
    (39, 22, "Xám Không Gian", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725523147/product%20colors/ipad-air-finish-space-gray-2024_sxdei1.png"),
    (40, 22, "Xanh Dương", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725523223/product%20colors/ipad-air-finish-blue-2024_pfrckt.png"),
    (41, 22, "Tím", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725523260/product%20colors/ipad-air-finish-purple-2024_n31itj.png"),
    (42, 22, "Ánh Sao", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725523277/product%20colors/ipad-air-finish-starlight-2024_m2y42a.png"),
    
    (43, 24, "Xanh Dương", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725523380/product%20colors/ipad-10th-gen-finish-blue-2022_rbneiv.png"),
    (44, 24, "Hồng", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725523416/product%20colors/ipad-10th-gen-finish-pink-2022_cpjh4g.png"),
    (45, 24, "Vàng", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725523420/product%20colors/ipad-10th-gen-finish-yellow-2022_m9afk4.png"),
    (46, 24, "Bạc", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725523437/product%20colors/ipad-10th-gen-finish-silver-2022_iw0yfy.png"),
    
    (47, 26, "Xám Không Gian", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725523147/product%20colors/ipad-air-finish-space-gray-2024_sxdei1.png"),
    (48, 26, "Hồng", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725523555/product%20colors/ipad-mini-finish-pink-2022_mijdei.png"),
	(49, 26, "Tím", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725523260/product%20colors/ipad-air-finish-purple-2024_n31itj.png"),
    (50, 26, "Ánh Sao", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725523277/product%20colors/ipad-air-finish-starlight-2024_m2y42a.png"),
    
    (51, 28, "Hồng", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725524443/product%20colors/watch-case-45-aluminum-pink-nc-s9_SW_COLOR_hhmt2s.jpg"),
    (52, 28, "Đêm Xanh Thẳm", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725524467/product%20colors/watch-case-41-aluminum-midnight-cell-s9_SW_COLOR_i2md1r.jpg"),
	(53, 28, "Ánh Sao", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725524487/product%20colors/watch-case-41-aluminum-starlight-cell-s9_SW_COLOR_uofwbe.jpg"),
    (54, 28, "Bạc", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725524500/product%20colors/watch-case-45-aluminum-silver-nc-s9_SW_COLOR_idmayk.jpg"),
    (54, 28, "Đỏ", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725524503/product%20colors/watch-case-45-aluminum-red-nc-s9_SW_COLOR_tsezvr.jpg"),
    (55, 28, "Gold", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725524649/product%20colors/watch-case-45-stainless-gold-s9_SW_COLOR_xhwbls.jpg"),
    (56, 28, "Bạc", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725524654/product%20colors/watch-case-45-stainless-silver-s9_SW_COLOR_aori01.jpg"),
    (57, 28, "Than Chì", "https://res.cloudinary.com/dipiog2a2/image/upload/v1725524658/product%20colors/watch-case-41-stainless-graphite-s9_SW_COLOR_aygwmx.jpg"),
    
    
    
 
INSERT INTO category(id, url_key, name) VALUES (7, "uncategorized", "Chưa phân loại");


-- product
INSERT INTO product
    (id, name, category_id,  lowest_price, description, thumbnail_url, slug)
VALUES
    (1, "MacBook Air 15 inch M3", 1, 32999000.00, "MacBook Air dễ dàng xử lý cả công việc lẫn giải trí và chip M3 mang đến năng lực tuyệt vời cho chiếc máy tính xách tay siêu gọn nhẹ này. Với thời lượng pin lên đến 18 giờ, bạn có thể mang theo chiếc MacBook Air siêu gọn nhẹ này đi bất cứ nơi đâu và nhanh chóng hoàn thành mọi việc bạn muốn.", "https://res.cloudinary.com/dipiog2a2/image/upload/v1713168824/mac%20air%2013%20inch/mba13-midnight-select-202402_e9nuox.jpg", "macbook-air-15-inch-m3"),
    (2, "MacBook Air 13 inch M3", 1, 27999000.00, "MacBook Air dễ dàng xử lý cả công việc lẫn giải trí và chip M3 mang đến năng lực tuyệt vời cho chiếc máy tính xách tay siêu gọn nhẹ này. Với thời lượng pin lên đến 18 giờ, bạn có thể mang theo chiếc MacBook Air siêu gọn nhẹ này đi bất cứ nơi đâu và nhanh chóng hoàn thành mọi việc bạn muốn.", "https://res.cloudinary.com/dipiog2a2/image/upload/v1713168824/mac%20air%2013%20inch/mba13-midnight-select-202402_e9nuox.jpg", "macbook-air-13-inch-m3"),
    (3, "MacBook Air 13 inch M2", 1, 24999000.00, "MacBook Air dễ dàng xử lý cả công việc lẫn giải trí và chip M2 mang đến năng lực tuyệt vời cho chiếc máy tính xách tay siêu gọn nhẹ này. Với thời lượng pin lên đến 18 giờ, bạn có thể mang theo chiếc MacBook Air siêu gọn nhẹ này đi bất cứ nơi đâu và nhanh chóng hoàn thành mọi việc bạn muốn.", "https://res.cloudinary.com/dipiog2a2/image/upload/v1713168824/mac%20air%2013%20inch/mba13-midnight-select-202402_e9nuox.jpg", "macbook-air-13-inch-m2"),

    (4, "MacBook Pro 14 inch M3", 1, 39999000.00, "MacBook Pro chạy cực nhanh với chip M3, M3 Pro và M3 Max. Được thiết kế trên công nghệ 3 nanômét và sở hữu kiến trúc GPU hoàn toàn mới, đây là dòng chip tiên tiến nhất từng được thiết kế cho máy tính cá nhân. Và mỗi mẫu chip trong đó đều mang đến nhiều tính năng và hiệu năng chuyên nghiệp hơn.","https://res.cloudinary.com/dipiog2a2/image/upload/v1725411508/mac%20pro%2014%20inch/mbp14-spacegray-select-202310_lcqm9i.jpg","macbook-pro-14-inch-m3");

-- product item
INSERT INTO product_item
    (id, product_id, sku, qty_in_stock, price, image_url, slug)
VALUES
    (1, 1, "MacBook Air 15 inch M3 Đêm Xanh Thẳm 8GB/256GB", 89, 32999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724922154/mac%20air%20m15%20inch/mba15-m3-digitalmat-gallery-1-202402_j6fzhs.png","macbook-air-15-inch-m3-dem-xanh-tham-8gb-256gb"),
    (2, 1, "MacBook Air 15 inch M3 Đêm Xanh Thẳm 8GB/512GB", 89, 37999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724922154/mac%20air%20m15%20inch/mba15-m3-digitalmat-gallery-1-202402_j6fzhs.png","macbook-air-15-inch-m3-dem-xanh-tham-8gb-512gb"),
    (3, 1, "MacBook Air 15 inch M3 Đêm Xanh Thẳm 16GB/512GB", 89, 42999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724922154/mac%20air%20m15%20inch/mba15-m3-digitalmat-gallery-1-202402_j6fzhs.png","macbook-air-15-inch-m3-dem-xanh-tham-16gb-512gb"),
    (4, 1, "MacBook Air 15 inch M3 Xám Không Gian 8GB/256GB", 89, 32999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725412977/mac%20air%20m15%20inch/mba15-spacegray-select-202306_cnn72o.jpg","macbook-air-15-inch-m3-xam-khong-gian-8gb-256gb"),
    (5, 1, "MacBook Air 15 inch M3 Xám Không Gian 8GB/512GB", 89, 37999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725412977/mac%20air%20m15%20inch/mba15-spacegray-select-202306_cnn72o.jpg","macbook-air-15-inch-m3-xam-khong-gian-8gb-512gb"),
    (6, 1, "MacBook Air 15 inch M3 Xám Không Gian 16GB/512GB", 89, 42999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725412977/mac%20air%20m15%20inch/mba15-spacegray-select-202306_cnn72o.jpg","macbook-air-15-inch-m3-xam-khong-gian-16gb-512gb"),
    (7, 1, "MacBook Air 15 inch M3 Ánh Sao 8GB/256GB", 89, 32999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725412909/mac%20air%20m15%20inch/mba15-starlight-select-202306_wjlitq.jpg", "macbook-air-15-inch-m3-anh-sao-8gb-256gb"),
    (8, 1, "MacBook Air 15 inch M3 Ánh Sao 8GB/512GB", 89, 37999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725412909/mac%20air%20m15%20inch/mba15-starlight-select-202306_wjlitq.jpg", "macbook-air-15-inch-m3-anh-sao-8gb-512gb"),
    (9, 1, "MacBook Air 15 inch M3 Ánh Sao 16GB/512GB", 89, 42999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725412909/mac%20air%20m15%20inch/mba15-starlight-select-202306_wjlitq.jpg", "macbook-air-15-inch-m3-anh-sao-16gb-512gb"),
    (10, 1, "MacBook Air 15 inch M3 Bạc 8GB/256GB", 89, 32999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725413256/mac%20air%20m15%20inch/mba15-silver-select-202306_cxzl9k.jpg", "macbook-air-15-inch-m3-bac-8gb-256gb"),
    (11, 1, "MacBook Air 15 inch M3 Bạc 8GB/512GB", 89, 37999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725413256/mac%20air%20m15%20inch/mba15-silver-select-202306_cxzl9k.jpg", "macbook-air-15-inch-m3-bac-8gb-512gb"),
    (12, 1, "MacBook Air 15 inch M3 Bạc 16GB/512GB", 89, 42999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725413256/mac%20air%20m15%20inch/mba15-silver-select-202306_cxzl9k.jpg", "macbook-air-15-inch-m3-bac-16gb-512gb"),

    (13, 2, "MacBook Air 13 inch M3 Đêm Xanh Thẳm 8GB/256GB", 89, 27999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713168824/mac%20air%2013%20inch/mba13-midnight-select-202402_e9nuox.jpg","macbook-air-13-inch-m3-dem-xanh-tham-8gb-256gb"),
    (14, 2, "MacBook Air 13 inch M3 Đêm Xanh Thẳm 8GB/512GB", 89, 32999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713168824/mac%20air%2013%20inch/mba13-midnight-select-202402_e9nuox.jpg","macbook-air-13-inch-m3-dem-xanh-tham-8gb-512gb"),
    (15, 2, "MacBook Air 13 inch M3 Đêm Xanh Thẳm 16GB/512GB", 89, 37999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713168824/mac%20air%2013%20inch/mba13-midnight-select-202402_e9nuox.jpg","macbook-air-13-inch-m3-dem-xanh-tham-16gb-512gb"),
    (16, 2, "MacBook Air 13 inch M3 Xám Không Gian 8GB/256GB", 89, 27999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572596/mac%20air%2013%20inch/mba13-spacegray-select-202402_bmmoag.jpg","macbook-air-13-inch-m3-xam-khong-gian-8gb-256gb"),
    (17, 2, "MacBook Air 13 inch M3 Xám Không Gian 8GB/512GB", 89, 32999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572596/mac%20air%2013%20inch/mba13-spacegray-select-202402_bmmoag.jpg","macbook-air-13-inch-m3-xam-khong-gian-8gb-512gb"),
    (18, 2, "MacBook Air 13 inch M3 Xám Không Gian 16GB/512GB", 89, 37999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572596/mac%20air%2013%20inch/mba13-spacegray-select-202402_bmmoag.jpg","macbook-air-13-inch-m3-xam-khong-gian-16gb-512gb"),
    (19, 2, "MacBook Air 13 inch M3 Ánh Sao 8GB/256GB", 89, 27999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572568/mac%20air%2013%20inch/mba13-starlight-select-202402_gwgl7n.jpg", "macbook-air-13-inch-m3-anh-sao-8gb-256gb"),
    (20, 2, "MacBook Air 13 inch M3 Ánh Sao 8GB/512GB", 89, 32999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572568/mac%20air%2013%20inch/mba13-starlight-select-202402_gwgl7n.jpg", "macbook-air-13-inch-m3-anh-sao-8gb-512gb"),
    (21, 2, "MacBook Air 13 inch M3 Ánh Sao 16GB/512GB", 89, 37999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572568/mac%20air%2013%20inch/mba13-starlight-select-202402_gwgl7n.jpg", "macbook-air-13-inch-m3-anh-sao-16gb-512gb"),
    (22, 2, "MacBook Air 13 inch M3 Bạc 8GB/256GB", 89, 27999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572608/mac%20air%2013%20inch/mba13-silver-select-202402_l7qwfq.jpg", "macbook-air-13-inch-m3-bac-8gb-256gb"),
    (23, 2, "MacBook Air 13 inch M3 Bạc 8GB/512GB", 89, 32999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572608/mac%20air%2013%20inch/mba13-silver-select-202402_l7qwfq.jpg", "macbook-air-13-inch-m3-bac-8gb-512gb"),
    (24, 2, "MacBook Air 13 inch M3 Bạc 16GB/512GB", 89, 37999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572608/mac%20air%2013%20inch/mba13-silver-select-202402_l7qwfq.jpg", "macbook-air-13-inch-m3-bac-16gb-512gb"),

	(25, 3, "MacBook Air 13 inch M2 Đêm Xanh Thẳm 8GB/256GB", 89, 24999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713168824/mac%20air%2013%20inch/mba13-midnight-select-202402_e9nuox.jpg","macbook-air-13-inch-m2-dem-xanh-tham-8gb-256gb"),
    (26, 3, "MacBook Air 13 inch M2 Đêm Xanh Thẳm 8GB/512GB", 25, 29999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713168824/mac%20air%2013%20inch/mba13-midnight-select-202402_e9nuox.jpg","macbook-air-13-inch-m2-dem-xanh-tham-8gb-512gb"),
	(27, 3, "MacBook Air 13 inch M2 Xám Không Gian 8GB/256GB", 49, 24999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572596/mac%20air%2013%20inch/mba13-spacegray-select-202402_bmmoag.jpg","macbook-air-13-inch-m2-xam-khong-gian-8gb-256gb"),
    (28, 3, "MacBook Air 13 inch M2 Xám Không Gian 8GB/512GB", 29, 29999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572596/mac%20air%2013%20inch/mba13-spacegray-select-202402_bmmoag.jpg","macbook-air-13-inch-m2-xam-khong-gian-8gb-512gb"),
    (29, 3, "MacBook Air 13 inch M2 Ánh Sao 8GB/256GB", 89, 24999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572568/mac%20air%2013%20inch/mba13-starlight-select-202402_gwgl7n.jpg", "macbook-air-13-inch-m2-anh-sao-8gb-256gb"),
    (30, 3, "MacBook Air 13 inch M2 Ánh Sao 8GB/512GB", 59, 29999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572568/mac%20air%2013%20inch/mba13-starlight-select-202402_gwgl7n.jpg", "macbook-air-13-inch-m2-anh-sao-8gb-512gb"),
    (31, 3, "MacBook Air 13 inch M2 Bạc 8GB/256GB", 89, 24999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572608/mac%20air%2013%20inch/mba13-silver-select-202402_l7qwfq.jpg", "macbook-air-13-inch-m2-bac-8gb-256gb"),
    (32, 3, "MacBook Air 13 inch M2 Bạc 8GB/512GB", 89, 29999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724572608/mac%20air%2013%20inch/mba13-silver-select-202402_l7qwfq.jpg", "macbook-air-13-inch-m2-bac-8gb-512gb"),

    (33, 4, "MacBook Pro 14 inch M3 Xám Không Gian 8GB/512GB", 89, 39999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725411508/mac%20pro%2014%20inch/mbp14-spacegray-select-202310_lcqm9i.jpg", "macbook-pro-14-inch-m3-xam-khong-gian-8gb-512gb"),
    (34, 4, "MacBook Pro 14 inch M3 Xám Không Gian 8GB/1TB", 89, 44999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725411508/mac%20pro%2014%20inch/mbp14-spacegray-select-202310_lcqm9i.jpg", "macbook-pro-14-inch-m3-xam-khong-gian-8gb-1tb"),
    (35, 4, "MacBook Pro 14 inch M3 Xám Không Gian 16GB/1TB", 89, 49999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725411508/mac%20pro%2014%20inch/mbp14-spacegray-select-202310_lcqm9i.jpg", "macbook-pro-14-inch-m3-xam-khong-gian-16gb-1tb"),
	(36, 4, "MacBook Pro 14 inch M3 Bạc 8GB/512GB", 89, 39999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725411514/mac%20pro%2014%20inch/mbp14-silver-select-202310_lzvlo4.jpg", "macbook-pro-14-inch-m3-bac-8gb-512gb"),
    (37, 4, "MacBook Pro 14 inch M3 Bạc 8GB/1TB", 89, 44999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725411514/mac%20pro%2014%20inch/mbp14-silver-select-202310_lzvlo4.jpg", "macbook-pro-14-inch-m3-bac-8gb-1tb"),
    (38, 4, "MacBook Pro 14 inch M3 Bạc 16GB/1TB", 89, 49999000.00, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725411514/mac%20pro%2014%20inch/mbp14-silver-select-202310_lzvlo4.jpg", "macbook-pro-14-inch-m3-bac-16gb-1tb");

-- product image
INSERT INTO product_image
    (id, product_id, url)
VALUES
    (1, 1, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724922154/mac%20air%20m15%20inch/mba15-m3-digitalmat-gallery-1-202402_j6fzhs.png"),
    (2, 1, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724922474/mac%20air%20m15%20inch/mba15-m3-digitalmat-gallery-2-202402_xnjhha.png"),
    (3, 1, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724922734/mac%20air%20m15%20inch/mba15-m3-digitalmat-gallery-3-202402_p2ps9l.png"),
    (4, 1, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724922747/mac%20air%20m15%20inch/mba15-m3-digitalmat-gallery-4-202402_GEO_VN_sylv1t.png"),
    (5, 1, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724922761/mac%20air%20m15%20inch/mba15-m3-digitalmat-gallery-5-202402_v3c3ti.png"),
    (6, 1, "https://res.cloudinary.com/dipiog2a2/image/upload/v1724922858/mac%20air%20m15%20inch/mba15-m3-digitalmat-gallery-6-202402_m76r0e.jpg"),
    
    (7, 2, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713168824/mac%20air%2013%20inch/mba13-midnight-select-202402_e9nuox.jpg"),
    (8, 2, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713173911/mac%20air%2013%20inch/mba13-m2-digitalmat-gallery-2-202402_ytygsm.png"),
    (9, 2, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713717058/mac%20air%2013%20inch/mba13-m2-digitalmat-gallery-3-202402_iiyc3e.png"),
    (10, 2, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713717245/mac%20air%2013%20inch/mba13-m2-digitalmat-gallery-4-202402_GEO_VN_umfcyw.png"),
    (11, 2, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713717263/mac%20air%2013%20inch/mba13-m2-digitalmat-gallery-5-202402_frsbhw.png"),
    (12, 2, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713717310/mac%20air%2013%20inch/macbook-air-digitalmat-gallery-7-202206_j1g2hj.png"),
    
    (13, 3, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713168824/mac%20air%2013%20inch/mba13-midnight-select-202402_e9nuox.jpg"),
    (14, 3, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713173911/mac%20air%2013%20inch/mba13-m2-digitalmat-gallery-2-202402_ytygsm.png"),
    (15, 3, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713717058/mac%20air%2013%20inch/mba13-m2-digitalmat-gallery-3-202402_iiyc3e.png"),
    (16, 3, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713717245/mac%20air%2013%20inch/mba13-m2-digitalmat-gallery-4-202402_GEO_VN_umfcyw.png"),
    (17, 3, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713717263/mac%20air%2013%20inch/mba13-m2-digitalmat-gallery-5-202402_frsbhw.png"),
    (18, 3, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713717310/mac%20air%2013%20inch/macbook-air-digitalmat-gallery-7-202206_j1g2hj.png"),

    (19, 4, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725411508/mac%20pro%2014%20inch/mbp14-spacegray-select-202310_lcqm9i.jpg"),
    (20, 4, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725414806/mac%20pro%2014%20inch/mbp-14-digitalmat-gallery-2-202310_o0vmar.png"),
    (21, 4, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725414825/mac%20pro%2014%20inch/mbp-14-digitalmat-gallery-3-202310_pivy24.png"),
    (22, 4, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725414838/mac%20pro%2014%20inch/mbp-14-digitalmat-gallery-4-202310_GEO_VN_e9jxtr.png"),
    (23, 4, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725414850/mac%20pro%2014%20inch/mbp-14-digitalmat-gallery-5-202310_GEO_VN_eq4voc.png"),
    (24, 4, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725414856/mac%20pro%2014%20inch/mbp-14-digitalmat-gallery-6-202310_n6wgye.png"),
    (25, 4, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725414865/mac%20pro%2014%20inch/mbp-14-digitalmat-gallery-7-202310_kqdbhq.png"),
    (26, 4, "https://res.cloudinary.com/dipiog2a2/image/upload/v1725414869/mac%20pro%2014%20inch/mbp-14-digitalmat-gallery-8-202310_pcmb2y.png");


    
    

    
 
    
    
    
    
-- product attribute
INSERT INTO product_attribute
    (id, product_id, `key`, value)
VALUES
    (1, 1, "Màn hình", "Liquid Retina (2880 x 1864)"),
    (2, 1, "Card màn hình", "Card tích hợp, 10 nhân GPU"),
    (3, 1, "Cổng kết nối", "MagSafe 3, Jack tai nghe 3.5 mm, 2 x Thunderbolt 3 / USB 4 (lên đến 40 Gb/s)"),
    (4, 1, "Đặc biệt", "Có đèn bàn phím"),
    (5, 1, "Hệ điều hành", "macOS Sonoma"),
    (6, 1, "Thiết kế", "Vỏ nhôm tái chế 100%"),
    (7, 1, "Kích thước, khối lượng", "Dài 340.4 mm - Rộng 237.6 mm - Dày 11.5 mm, 1.51 kg"),
    (8, 1, "Thời điểm ra mắt", "2024");

