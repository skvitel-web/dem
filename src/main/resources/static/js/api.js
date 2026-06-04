const API = {
  async request(url, options = {}) {
    const response = await fetch(url, {
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        ...(options.headers || {}),
      },
      ...options,
    });

    let body = null;
    const text = await response.text();
    if (text) {
      try {
        body = JSON.parse(text);
      } catch {
        body = { message: text };
      }
    }

    if (!response.ok) {
      const message = body?.message || body?.errors || "Ошибка запроса";
      const err = new Error(typeof message === "string" ? message : "Ошибка запроса");
      err.status = response.status;
      err.body = body;
      throw err;
    }
    return body;
  },

  register(data) {
    return this.request("/api/auth/register", { method: "POST", body: JSON.stringify(data) });
  },

  login(data) {
    return this.request("/api/auth/login", { method: "POST", body: JSON.stringify(data) });
  },

  logout() {
    return this.request("/api/auth/logout", { method: "POST" });
  },

  me() {
    return this.request("/api/auth/me");
  },

  courses() {
    return this.request("/api/courses");
  },

  myApplications() {
    return this.request("/api/applications");
  },

  createApplication(data) {
    return this.request("/api/applications", { method: "POST", body: JSON.stringify(data) });
  },

  addReview(id, text) {
    return this.request(`/api/applications/${id}/review`, {
      method: "POST",
      body: JSON.stringify({ text }),
    });
  },

  adminApplications(params) {
    const q = new URLSearchParams(params).toString();
    return this.request(`/api/admin/applications?${q}`);
  },

  updateStatus(id, status) {
    return this.request(`/api/admin/applications/${id}/status`, {
      method: "PUT",
      body: JSON.stringify({ status }),
    });
  },
};
