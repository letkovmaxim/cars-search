package org.cars.contollers;

import org.cars.models.Mark;
import org.cars.models.Model;
import org.cars.models.Modification;
import org.cars.models.SearchResult;
import org.cars.services.MarkService;
import org.cars.services.ModelService;
import org.cars.services.ModificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class SearchController {

    @Autowired
    MarkService markService;
    @Autowired
    ModelService modelService;
    @Autowired
    ModificationService modificationService;


    @GetMapping("/search")
    public SearchResult search(@RequestParam(name = "mark") Optional<String> markName,
                               @RequestParam(name = "model") Optional<String> modelName,
                               @RequestParam(name = "modification") Optional<String> modificationName,
                               @RequestParam(name = "periodBegin") Optional<Integer> periodBegin,
                               @RequestParam(name = "periodEnd") Optional<Integer> periodEnd) {
        List<Mark> resultMarks = new ArrayList<>();
        List<Model> resultModels = new ArrayList<>();
        List<Modification> resultModifications = new ArrayList<>();
        SearchResult searchResult = new SearchResult();
        int count = 0;

        //Заданы модификация и/или периоды
        if (modificationName.isPresent() && periodBegin.isPresent() && periodEnd.isPresent()) {
            resultModifications = modificationService.getByNameAndPeriodBeginAndPeriodEnd(modificationName.get(), periodBegin.get(), periodEnd.get());
        } else if (modificationName.isPresent() && periodBegin.isPresent()) {
            resultModifications = modificationService.getByNameAndPeriodBegin(modificationName.get(), periodBegin.get());
        } else if (modificationName.isPresent() && periodEnd.isPresent()) {
            resultModifications = modificationService.getByNameAndPeriodEnd(modificationName.get(), periodEnd.get());
        } else if (periodBegin.isPresent() && periodEnd.isPresent()) {
            resultModifications = modificationService.getByPeriodBeginAndPeriodEnd(periodBegin.get(), periodEnd.get());
        } else if (periodBegin.isPresent()) {
            resultModifications = modificationService.getByPeriodBegin(periodBegin.get());
        } else if (periodEnd.isPresent()) {
            resultModifications = modificationService.getByPeriodEnd(periodEnd.get());
        } else if (modificationName.isPresent()) {
            resultModifications = modificationService.getByName(modificationName.get());
        } else { //Ни модификация, ни периоды не заданы. Ищем по модели
            if (modelName.isPresent()) {
                resultModels = modelService.getByName(modelName.get());
            } else { //Задана только марка
                if (markName.isPresent()) {
                    resultMarks = markService.getByName(markName.get());
                }
            }
        }

        //Получаем список марок
        if (!resultMarks.isEmpty()) {
            searchResult.setMarks(resultMarks);

        } else if (!resultModels.isEmpty()) {
            for (Model model : resultModels) {
                if (markName.isPresent()) {
                    if (markName.get().equalsIgnoreCase(model.getMark().getName())) {
                        searchResult.getMarks().add(model.getMark());
                    }
                } else {
                    searchResult.getMarks().add(model.getMark());
                }
            }
        } else if (!resultModifications.isEmpty()) {
            for (Modification modification : resultModifications) {

                if (markName.isPresent() && modelName.isPresent()) {
                    if (markName.get().equalsIgnoreCase(modification.getModel().getMark().getName()) &&
                            modelName.get().equalsIgnoreCase(modification.getModel().getName())) {
                        if (!searchResult.getMarks().contains(modification.getModel().getMark())) {
                            searchResult.getMarks().add(modification.getModel().getMark());
                        }
                    }

                } else if (modelName.isPresent()) {
                    if (modelName.get().equalsIgnoreCase(modification.getModel().getName())) {
                        if (!searchResult.getMarks().contains(modification.getModel().getMark())) {
                            searchResult.getMarks().add(modification.getModel().getMark());
                        }
                    }

                } else if (markName.isPresent()) {
                    if (markName.get().equalsIgnoreCase(modification.getModel().getMark().getName())) {
                        if (!searchResult.getMarks().contains(modification.getModel().getMark())) {
                            searchResult.getMarks().add(modification.getModel().getMark());
                        }
                    }

                } else {
                    if (!searchResult.getMarks().contains(modification.getModel().getMark())) {
                        searchResult.getMarks().add(modification.getModel().getMark());
                    }
                }
            }
        }

        //Удаляем ненужные модели и модификации
        List<Mark> tmpMarks = new ArrayList<>(searchResult.getMarks());
        for (Mark mark : tmpMarks) {

            List<Model> tmpModels = new ArrayList<>(mark.getModels());
            for (Model model : tmpModels) {

                List<Modification> tmpModifications = new ArrayList<>(model.getModifications());
                //Если задано имя модификации, то удаляем модификации с другим именем
                if (modificationName.isPresent()) {
                    for (Modification modification : tmpModifications) {
                        if (!modificationName.get().equalsIgnoreCase(modification.getName())) {
                            model.getModifications().remove(modification);
                        }
                    }
                }

                //Если заданы периоды, то удаляем модификации с другими периодами
                if (periodBegin.isPresent()) {
                    for (Modification modification : tmpModifications) {
                        if (modification.getPeriodEnd() < periodBegin.get()) {
                            model.getModifications().remove(modification);
                        }
                    }
                }

                if (periodEnd.isPresent()) {
                    for (Modification modification : tmpModifications) {
                        if (modification.getPeriodBegin() > periodEnd.get()) {
                            model.getModifications().remove(modification);
                        }
                    }
                }

                //Если задано имя модели, то удаляем модели с другим именем
                if (modelName.isPresent()) {
                    if (!modelName.get().equalsIgnoreCase(model.getName())) {
                        mark.getModels().remove(model);
                    }
                }

                if (model.getModifications().isEmpty()) {
                    mark.getModels().remove(model);
                }
            }
        }

        //Считаем количество машин
        for (Mark mark : searchResult.getMarks()) {
            for (Model model : mark.getModels()) {
                for (Modification modification : model.getModifications()) {
                    count++;
                }
            }
        }
        searchResult.setCount(count);

        return searchResult;
    }
}
