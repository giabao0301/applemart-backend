import React, { useState } from "react";

const CartItem = () => {
  const [quantity, setQuantity] = useState<number>(1);

  const increaseQuantityHandler = () => {
    setQuantity((prevQuantity) => prevQuantity + 1);
  };

  const descreaseQuantityHandler = () => {
    if (quantity > 1) {
      setQuantity((prevQuantity) => prevQuantity - 1);
    }
  };

  const quantityChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (+e.target.value > 0) {
      setQuantity(+e.target.value);
    }
  };

  return (
    <div className="flex items-center shadow mb-8">
      <div className="basis-1/5">
        <img
          className="block h-[13.5294117647rem] w-auto my-0"
          src="https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/mba13-m2-digitalmat-gallery-1-202402?wid=728&hei=666&fmt=png-alpha&.v=1707263976493"
          alt=""
        />
      </div>
      <div className="basis-1/4">
        <h3 className="text-3xl">
          MacBook Air
          <br />
          13 inch với chip M2
        </h3>
      </div>
      <div className="basis-1/6 flex">
        <label htmlFor="color" className="text-xl mr-2">
          Màu:
        </label>
        <select name="color" id="color" className="text-xl w-auto ">
          <option value="1">Silver</option>
          <option value="2">Space Gray</option>
        </select>
      </div>
      <div className="basis-1/12 flex justify-between items-center rounded-sm border">
        <button
          className="text-primary text-2xl px-4 border-r-2"
          onClick={descreaseQuantityHandler}
        >
          -
        </button>
        <input
          className="text-primary text-2xl text-center w-12 focus:outline-none border-b border-solid border-[#d6d6d6]"
          type="text"
          value={quantity}
          onChange={quantityChangeHandler}
        />
        <button
          className="text-primary text-2xl px-4 border-l-2"
          onClick={increaseQuantityHandler}
        >
          +
        </button>
      </div>
      <div className="basis-1/4 flex flex-col text-center">
        <span className="text-2xl tracking-wide mb-4">24.999.000đ</span>
        <span className="text-[#0070c9] text-lg cursor-pointer">Xóa</span>
      </div>
    </div>
  );
};

export default CartItem;
