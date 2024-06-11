import { NavLink } from "react-router-dom";
import appleLogo from "../../src/assets/apple.svg";
import searchIcon from "../../src/assets/search.svg";
import cartIcon from "../../src/assets/cart.svg";
import accountIcon from "../../src/assets/account.svg";

const MainNavigation = () => {
  return (
    <nav className="block h-12 fixed top-0 right-0 left-0 bottom-0 z-[999] bg-white/80 backdrop-blur-sm">
      <div className="max-w-screen-lg w-full mx-auto my-0">
        <ul className="flex justify-between w-auto items-center">
          <li>
            <NavLink to={"/"}>
              <img src={appleLogo} alt="Apple logo" />
            </NavLink>
          </li>
          <li>
            <NavLink to={"/"}>Cửa hàng</NavLink>
          </li>

          <li className="max-w-[480px] w-full px-4">
            <div className="relative">
              <input
                type="text"
                name="q"
                className="text-sm tracking-wide w-full h-8 shadow-xl p-4 rounded-full bg-white focus:outline-none focus:ring-1 focus:ring-secondary focus:ring-opacity-50"
                placeholder="Tìm kiếm"
              />
              <div className="h-5 w-5 absolute bottom-4 right-2 mb-[2px]">
                <img src={searchIcon} alt="" />
              </div>
            </div>
          </li>
          <li>
            <NavLink to={"cart"}>
              <img src={cartIcon} alt="" />
            </NavLink>
          </li>
          <li>
            <NavLink to={"account"} className="flex gap-2 items-center">
              <img src={accountIcon} alt="" />
              <span>Tài khoản</span>
            </NavLink>
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default MainNavigation;
