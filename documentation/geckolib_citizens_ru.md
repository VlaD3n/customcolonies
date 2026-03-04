# GeckoLib кастомизация жителей MineColonies (1.21-пайплайн)

> Реализовано для кода мода с поддержкой GeckoLib и автозаменой рендера **всех** жителей/посетителей без UUID.

## Что изменено

- Все жители и посетители рендерятся через `GeckoCitizenRenderer` (GeckoLib).
- Мод при запуске создает:
  - `./minecolonies/citizen_assets/citizen_rendering.json`
  - `./resourcepacks/minecolonies_citizen_assets/` (готовый resource pack каркас)
- Настройка в `citizen_rendering.json` применяется автоматически ко всем существующим и новым жителям.

## Как загрузить свою модель/анимацию/текстуру без изменения кода мода

1. Запусти игру 1 раз с модом.
2. Открой папку игры и зайди в:
   - `resourcepacks/minecolonies_citizen_assets/`
3. Скопируй файлы:
   - Модель: `assets/minecolonies/geo/citizen/custom/citizen.geo.json`
   - Анимация: `assets/minecolonies/animations/citizen/custom/citizen.animation.json`
   - Текстура: `assets/minecolonies/textures/entity/citizen/custom/citizen.png`
4. Включи resource pack `minecolonies_citizen_assets` в меню ресурс-паков Minecraft.
5. Отредактируй `minecolonies/citizen_assets/citizen_rendering.json`:

```json
{
  "enabled": true,
  "model": "minecolonies:geo/citizen/custom/citizen.geo.json",
  "texture": "minecolonies:textures/entity/citizen/custom/citizen.png",
  "animation": "minecolonies:animations/citizen/custom/citizen.animation.json"
}
```

6. Перезайди в мир (или перезапусти клиент).

## Пример пайплайна через Blockbench

1. Создай проект **GeckoLib Animated Model**.
2. Сделай кости минимум:
   - `root`
   - `body`, `head`, `arm_left`, `arm_right`, `leg_left`, `leg_right`
3. Нарисуй UV и экспортируй PNG текстуру (`64x64` или `128x128`).
4. Создай анимации:
   - `animation.citizen.idle`
   - `animation.citizen.walk`
   - `animation.citizen.sleep`
5. Экспортируй:
   - `Export GeckoLib Model` -> `citizen.geo.json`
   - `Export GeckoLib Animations` -> `citizen.animation.json`
6. Положи экспорт в пути из раздела выше.

## Важно

- UUID жителей не нужен: замена глобальная, автоматически для всех.
- Если `enabled=false`, используется дефолтный встроенный GeckoLib ресурс.
