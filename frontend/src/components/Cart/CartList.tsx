import CartItem from "./CartItem";

const CarList = () => {
  return (
    <div className="mt-[94px] mx-12">
      <div className="max-w-80 mx-auto border-b border-solid border-[#e7e7e8] pb-8">
        <h1 className="text-center text-4xl font-semibold">Giỏ hàng của bạn</h1>
        <button className="w-full tracking-wider mt-12 text-[18px] py-1 px-[15px] inline-block bg-gradient-to-b from-[#42a1ec] to-[#0070c9] text-white rounded hover:opacity-80">
          Thanh toán
        </button>
      </div>
      <ul>
        <li>
          <CartItem />
        </li>
        <li>
          <CartItem />
        </li>
        <li>
          <CartItem />
        </li>
      </ul>
    </div>
  );
};

export default CarList;
