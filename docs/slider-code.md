# Код слайдера (4 слайда, смена каждые 3 секунды, без картинок)

## HTML (вставить на главную страницу)

```html
<div class="slider slider--hero" id="mainSlider">
  <div class="slider-track">
    <div class="slider-slide" data-slide="1"></div>
    <div class="slider-slide" data-slide="2"></div>
    <div class="slider-slide" data-slide="3"></div>
    <div class="slider-slide" data-slide="4"></div>
  </div>
  <div class="slider-controls">
    <button type="button" class="slider-btn" data-slider-prev aria-label="Назад">‹</button>
    <button type="button" class="slider-btn" data-slider-next aria-label="Вперёд">›</button>
  </div>
  <div class="slider-dots"></div>
</div>

<script src="/js/slider.js"></script>
<script>
  initSlider(document.getElementById("mainSlider"), { interval: 3000 });
</script>
```

Чтобы добавить фото позже — в каждый `.slider-slide` вставьте `<img>` или задайте в CSS:

```css
.slider-slide[data-slide="1"] {
  background: url("/images/photo1.jpg") center / cover no-repeat;
}
```

## JavaScript (`/js/slider.js`)

Логика: 4 элемента `.slider-slide`, `setInterval` 3000 мс, сдвиг `.slider-track` через `translateX`, точки и кнопки «назад/вперёд».

Файл: `src/main/resources/static/js/slider.js`

## CSS (фрагмент)

Стили слайдера: `src/main/resources/static/css/styles.css` — блоки `.slider`, `.slider-track`, `.slider-slide`, `.slider-btn`, `.slider-dot`.
