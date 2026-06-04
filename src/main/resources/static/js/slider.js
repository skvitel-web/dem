/**
 * Слайдер: автопрокрутка каждые 3 с, кнопки «назад/вперёд», точки.
 * @param {HTMLElement} root — контейнер .slider
 * @param {{ onChange?: (index: number) => void, interval?: number }} options
 */
function initSlider(root, options = {}) {
  const intervalMs = options.interval ?? 3000;
  const onChange = options.onChange ?? (() => {});

  const track = root.querySelector(".slider-track");
  const slides = [...root.querySelectorAll(".slider-slide")];
  const prev = root.querySelector("[data-slider-prev]");
  const next = root.querySelector("[data-slider-next]");
  const dotsWrap = root.querySelector(".slider-dots");
  let index = 0;
  let timer;

  if (!track || !slides.length || !dotsWrap) {
    return;
  }

  slides.forEach((_, i) => {
    const dot = document.createElement("button");
    dot.type = "button";
    dot.className = "slider-dot" + (i === 0 ? " active" : "");
    dot.setAttribute("aria-label", `Слайд ${i + 1}`);
    dot.addEventListener("click", () => {
      goTo(i);
      restartTimer();
    });
    dotsWrap.appendChild(dot);
  });

  const dots = [...dotsWrap.querySelectorAll(".slider-dot")];

  function goTo(i) {
    index = (i + slides.length) % slides.length;
    track.style.transform = `translateX(-${index * 100}%)`;
    dots.forEach((d, j) => d.classList.toggle("active", j === index));
    onChange(index);
  }

  function nextSlide() {
    goTo(index + 1);
  }

  function prevSlide() {
    goTo(index - 1);
  }

  function restartTimer() {
    clearInterval(timer);
    timer = setInterval(nextSlide, intervalMs);
  }

  prev?.addEventListener("click", () => {
    prevSlide();
    restartTimer();
  });
  next?.addEventListener("click", () => {
    nextSlide();
    restartTimer();
  });

  root.addEventListener("mouseenter", () => clearInterval(timer));
  root.addEventListener("mouseleave", restartTimer);

  goTo(0);
  restartTimer();
}
