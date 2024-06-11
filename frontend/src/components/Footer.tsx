const Footer = () => {
  return (
    <footer className="flex flex-col space-y-10 justify-center m-10 relative min-w-[1024px] h-56 border-t border-solid border-[#e7e7e8]">
      <nav className="flex justify-center flex-wrap gap-6 text-gray-500 font-medium">
        <a className="hover:text-gray-900 text-sm" href="#">
          Cửa hàng
        </a>
        <a className="hover:text-gray-900 text-sm" href="#">
          Mac
        </a>
        <a className="hover:text-gray-900 text-sm" href="#">
          iPad
        </a>
        <a className="hover:text-gray-900 text-sm" href="#">
          iPhone
        </a>
        <a className="hover:text-gray-900 text-sm" href="#">
          Watch
        </a>
        <a className="hover:text-gray-900 text-sm" href="#">
          AirPods
        </a>
        <a className="hover:text-gray-900 text-sm" href="#">
          Phụ kiện
        </a>
      </nav>

      <div className="flex justify-center space-x-5">
        <a
          href="https://facebook.com"
          target="_blank"
          rel="noopener noreferrer"
        >
          <img src="https://img.icons8.com/fluent/30/000000/facebook-new.png" />
        </a>
        <a
          href="https://linkedin.com"
          target="_blank"
          rel="noopener noreferrer"
        >
          <img src="https://img.icons8.com/fluent/30/000000/linkedin-2.png" />
        </a>
        <a
          href="https://instagram.com"
          target="_blank"
          rel="noopener noreferrer"
        >
          <img src="https://img.icons8.com/fluent/30/000000/instagram-new.png" />
        </a>
        <a
          href="https://messenger.com"
          target="_blank"
          rel="noopener noreferrer"
        >
          <img src="https://img.icons8.com/fluent/30/000000/facebook-messenger--v2.png" />
        </a>
        <a href="https://twitter.com" target="_blank" rel="noopener noreferrer">
          <img src="https://img.icons8.com/fluent/30/000000/twitter.png" />
        </a>
      </div>
      <p className="text-center text-gray-700 font-medium text-sm">
        Bản quyền © 2024 Apple Inc. Bảo lưu mọi quyền.
      </p>
    </footer>
  );
};

export default Footer;
