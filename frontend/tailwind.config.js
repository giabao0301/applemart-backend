/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: "#1d1d1f",
        secondary: "#6e6e73",
        error: "#de071c",
      },
      boxShadow: {
        "product-card": "2px 4px 12px #00000014",
        "product-card-hover": "rgba(0, 0, 0, 0.16) 2px 4px 16px",
      },
      transitionTimingFunction: {
        ease: "cubic-bezier(0,0,.5,1)",
      },
      scale: {
        101: "1.01",
      },
    },
  },
  plugins: [],
};
