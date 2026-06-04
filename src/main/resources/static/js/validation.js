const Validation = {
  email(value) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
  },

  password(value) {
    return /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/.test(value);
  },

  phone(value) {
    return /^\+?[0-9\s\-()]{10,20}$/.test(value);
  },

  dateDdMmYyyy(value) {
    if (!/^\d{2}\.\d{2}\.\d{4}$/.test(value)) return false;
    const [d, m, y] = value.split(".").map(Number);
    const dt = new Date(y, m - 1, d);
    return dt.getFullYear() === y && dt.getMonth() === m - 1 && dt.getDate() === d;
  },

  showError(el, message) {
    if (!el) return;
    el.textContent = message || "";
  },
};
