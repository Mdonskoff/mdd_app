import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription, tap} from "rxjs";
import {FormControl, FormGroup} from "@angular/forms";
import {User} from "../../../shared/interfaces/user.interface";
import {Router} from "@angular/router";
import {Topic} from "../../../shared/interfaces/topic.interface";
import {ArticleItem} from "../interfaces/article-item.interface";
import {ArticleService} from "../services/article.service";
import {TopicService} from "../../../shared/services/topic.service";

@Component({
  selector: 'app-article-form',
  templateUrl: './article-form.component.html',
  styleUrls: ['./article-form.component.scss']
})
export class ArticleFormComponent implements OnInit, OnDestroy {

  topic!: Topic[];
  topic$!: Subscription;
  article$!: Subscription;
  articleFormGroup = new FormGroup({});
  theme = new FormControl("");
  articleTitle = new FormControl("");
  articleContain = new FormControl("");
  article: ArticleItem = {};
  user!: User;

  constructor(
    private topicService: TopicService,
    private articleService : ArticleService,
    private router : Router,
  ){}

  ngOnInit(): void {
    this.articleFormGroup.addControl('theme', this.theme);
    this.articleFormGroup.addControl('title', this.articleTitle);
    this.articleFormGroup.addControl('contain', this.articleContain);

    this.topic$ = this.topicService.getTopics().pipe(
      tap(tabTopics => {
        this.topic = tabTopics.data.Topics
        console.log(tabTopics)
      })
    ).subscribe();

  }
  ngOnDestroy(): void {
    if (this.topic$) {
      this.topic$.unsubscribe();
    }
    if (this.article$) {
      this.article$.unsubscribe();
    }
  }

  onSubmit(): void{
    this.article.title = this.articleTitle.value!;
    this.article.contents = this.articleContain.value!;
    this.article.idTopic = this.theme.value!;
    if (this.articleFormGroup.valid) {
      this.article$ = this.articleService.create(this.article).subscribe({
        next : () => this.router.navigate(['/article'])
      });
    }
  }

}
