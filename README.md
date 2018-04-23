# district-ui-component-meta-tags

Clojurescript [mount](https://github.com/tolitius/mount) + [re-frame](https://github.com/Day8/re-frame) component for a district UI, that provides [reagent](https://github.com/reagent-project/reagent) UI component for serving html meta-tags for SEO.

## Installation

Add `[district0x/district-ui-component-meta-tags "1.0.0"]` into your project.clj.<br/>
Include `[district.ui.component.meta-tags]` in your CLJS file.

## Usage

#### district.ui.component.meta-tags
This namespace contains reagent UI component to add meta tags.<br/>
<br/>
Basic usage:

```clojure
(ns my-district
  (:require [reagent.core :as r]
            [district.ui.component.meta-tags :as meta-tags]))

(defn main-panel []
  [:div#page1 [meta-tags/meta-tags {:title "My District" :description "A long and complete description of my district"}]])

(defn ^:export init []
  (r/render [main-panel] (.getElementById js/document "app")))
```

This will set the page title to *"My District"* and the content of the *description* meta tag.
You can also pass additional custom meta-tags by adding more maps with and `:id`, `:name` and `:coontent` after the `:title` and `:description` map:

```clojure
[meta-tags/meta-tags {:title "title" :description "description"}
 {:id "id0" :name "foo" :content "foo"}
 {:id "id1" :name "bar" :content "bar"}]
```
