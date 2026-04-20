# TLIG Design System (Slate & Gold Edition)

Tento dokument definuje vizuální styl aplikace založený na tmavém "Slate" tématu s doplňkovými akcenty.

## 1. Barevná paleta

### Základní (Core)
- **Background:** `#111827` (Velmi tmavá modro-šedá pro pozadí obrazovek)
- **Surface:** `#1A1F27` (Barva pro karty, modaly a oddělené sekce)
- **Border:** `#2A3441` (Jemné linky a ohraničení karet, průhlednost 40-60%)

### Texty
- **Content Primary:** `#F1F5F9` (Hlavní čitelný text)
- **Content Secondary:** `#94A3B8` (Vedlejší info, popisky)
- **Content Muted:** `#64748B` (Méně důležité texty)

### Akcenty (Accents)
- **Primary (Gold):** `#C69C6D` (Důležité ikony, vybrané stavy)
- **Info (Blue):** `#60A5FA` (Odkazy, informace)
- **Success (Green):** `#34D399` (Hotové věci)
- **Danger (Red):** `#EF4444` (Mazání, chyby)

---

## 2. Komponenty a Stylování

### Karty (Cards)
- **Pozadí:** `Surface`
- **Okraj:** 1dp `Border`
- **Zaoblení:** 12dp nebo 16dp
- **Vnitřní Padding:** 12dp - 16dp

### Modaly (Bottom Sheets / Dialogs)
- **Pozadí:** Musí odpovídat `Surface`. 
- **Adaptivita:** V `MessageScreen` se barva `Surface` a `Background` mění podle uživatelského nastavení. Komponenty uvnitř modalů musí barvy přebírat z aktuálního tématu, nikoliv z pevných konstant.

---

## 3. Pravidla pro MessageScreen (Čtecí režim)
U čtecího režimu je barva dynamická. 
- **Text:** Vždy musí mít dostatečný kontrast k `Background`.
- **UI prvky (tlačítka v modalu):** Musí používat `Content Primary` barvu odvozenou od pozadí (např. na bílém pozadí budou černé).

---

## 4. Typografie
- **Nadpisy:** Sans-serif (Inter/Roboto), SemiBold
- **Text poselství:** Serif (pro lepší čitelnost dlouhých textů)
- **UI popisky:** Sans-serif, Medium, 12-14sp
