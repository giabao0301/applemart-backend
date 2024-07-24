-- role
insert into role
	(name) 
values
    ("USER"),
    ("ADMIN"),
    ("STAFF");

-- category 
INSERT INTO category
	(id, url_key, name, image_url)
VALUES 
	(1, "mac", "Mac", "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/store-card-13-mac-nav-202310?wid=400&hei=260&fmt=png-alpha&.v=1696964122666"),
	(2, "iphone", "iPhone", "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/store-card-13-iphone-nav-202309?wid=400&hei=260&fmt=png-alpha&.v=1692971740452"),
	(3, "ipad", "iPad", "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/store-card-13-ipad-nav-202210?wid=400&hei=260&fmt=png-alpha&.v=1664912135437"),
	(4, "watch", "Apple Watch", "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/store-card-13-watch-nav-202309_GEO_VN?wid=400&hei=260&fmt=png-alpha&.v=1693703821972"),
	(5, "airpods", "AirPods", "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/store-card-13-airpods-nav-202209?wid=400&hei=260&fmt=png-alpha&.v=1660676485885"),
	(6, "accessories", "Phụ Kiện", "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/store-card-13-accessories-nav-202403?wid=400&hei=260&fmt=png-alpha&.v=1707850611597"),
	(7, "uncategorized", "Chưa phân loại");
-- product 
insert into product
	(id, name, category_id, description, image_url, slug) 
values
	(1, "MacBook Air 15 inch M3", 1, "MacBook Air dễ dàng xử lý cả công việc lẫn giải trí và chip M3 mang đến năng lực tuyệt vời cho chiếc máy tính xách tay siêu gọn nhẹ này. Với thời lượng pin lên đến 18 giờ, bạn có thể mang theo chiếc MacBook Air siêu gọn nhẹ này đi bất cứ nơi đâu và nhanh chóng hoàn thành mọi việc bạn muốn.", "https://res.cloudinary.com/dipiog2a2/image/upload/v1713168824/mba13-midnight-select-202402_e9nuox.jpg", "macbook-air-15-inch-m3");


-- product-item 
insert into product_item
	(id, product_id, sku, qty_in_stock, price, slug) 
values
	(1, 1, "MacBook Air 15 inch M3 Đêm Xanh Thẳm 8GB/256GB", 89, 32.999, "macbook-air-15-inch-m3-dem-xanh-tham-8gb-256gb");

-- product_image
insert into product_image
	(id, product_item_id, image_url) 
values
	(1, 1, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713168824/mba13-midnight-select-202402_e9nuox.jpg"),
	(2, 1, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713173911/mba13-m2-digitalmat-gallery-2-202402_ytygsm.png"),
	(3, 1, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713717058/mba13-m2-digitalmat-gallery-3-202402_iiyc3e.png"),
	(4, 1, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713717245/mba13-m2-digitalmat-gallery-4-202402_GEO_VN_umfcyw.png"),
	(5, 1, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713717263/mba13-m2-digitalmat-gallery-5-202402_frsbhw.png"),
	(6, 1, "https://res.cloudinary.com/dipiog2a2/image/upload/v1713717310/macbook-air-digitalmat-gallery-7-202206_j1g2hj.png");


-- product variation
insert into variation
	(id, category_id, name) 
values
	(1, 1, "color"),
    (2, 1, "ram"),
    (3, 1, "ssd");


-- product option
insert into variation_option
	(id, variation_id, value) 
values
	(1, 1, "Đêm Xanh Thẳm"),
	(2, 1, "Xám Không gian"),
	(3, 1, "Ánh Sao"),
	(4, 1, "Bạc"),
    (5, 2, "8GB"),
    (6, 2, "16GB"),
	(7, 3, "256GB"),
    (8, 3, "512GB");
    
    
-- product attribute
insert into product_attribute values(1, 1, "Màn hình", "Liquid Retina (2880 x 1864)");
insert into product_attribute values(2, 1, "Cổng kết nối", "MagSafe 3, Jack tai nghe 3.5 mm, 2 x Thunderbolt / USB 4 (hỗ trợ DisplayPort, Thunderbolt 3 (up to 40Gb/s), USB 4 (up to 40Gb/s))")

