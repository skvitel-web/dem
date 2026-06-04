/**
 * Хлебные крошки + кнопка «Назад».
 * data-trail='[{"href":"/","label":"Главная"},{"label":"Текущая"}]'
 */
function renderBreadcrumbs(container) {
  if (!container || !container.dataset.trail) return;

  let trail;
  try {
    trail = JSON.parse(container.dataset.trail);
  } catch {
    return;
  }

  container.innerHTML = "";
  container.className = "breadcrumbs-wrap";

  const backBtn = document.createElement("button");
  backBtn.type = "button";
  backBtn.className = "btn btn-secondary btn-sm bc-back";
  backBtn.textContent = "← Назад";
  backBtn.addEventListener("click", () => {
    if (window.history.length > 1) {
      window.history.back();
    } else {
      const home = trail.find((t) => t.href === "/")?.href || "/";
      window.location.href = home;
    }
  });
  container.appendChild(backBtn);

  const nav = document.createElement("nav");
  nav.className = "breadcrumbs";
  nav.setAttribute("aria-label", "Хлебные крошки");

  trail.forEach((item, i) => {
    if (i > 0) {
      const sep = document.createElement("span");
      sep.className = "bc-sep";
      sep.textContent = "›";
      sep.setAttribute("aria-hidden", "true");
      nav.appendChild(sep);
    }

    if (item.href && i < trail.length - 1) {
      const a = document.createElement("a");
      a.href = item.href;
      a.textContent = item.label;
      nav.appendChild(a);
    } else {
      const span = document.createElement("span");
      span.className = "bc-current";
      span.textContent = item.label;
      if (i === trail.length - 1) {
        span.setAttribute("aria-current", "page");
      }
      nav.appendChild(span);
    }
  });

  container.appendChild(nav);
}

document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll("[data-trail]").forEach(renderBreadcrumbs);
});
