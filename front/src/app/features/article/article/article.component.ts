import {Component, OnDestroy, OnInit} from '@angular/core';
import {ArticleItem} from "../interfaces/article-item.interface";
import {TopicService} from "../../../shared/services/topic.service";
import {ActivatedRoute} from "@angular/router";
import {ArticleService} from "../services/article.service";
import {mergeMap, Subscription, tap} from "rxjs";
import {Topic} from "../../../shared/interfaces/topic.interface";

//Aller sur un article
@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit, OnDestroy {

  articleItem!: ArticleItem;
  articleItem$! : Subscription;
  topic!: Topic;

  constructor(
    private route: ActivatedRoute,
    private articleService : ArticleService,
    private topicService: TopicService
  ){}


  ngOnInit(): void {
    this.articleItem$ = this.route.queryParams.pipe(
      mergeMap(param => {
        return this.articleService.getArticleById(param['id']);
      }),
      tap(article => {
        this.articleItem = article;
        this.topic.id = article.idTopic!
        this.topic.label = article.labelTopic!
      })
    ).subscribe();

  }
  ngOnDestroy(): void {
    this.articleItem$.unsubscribe();
  }


}
