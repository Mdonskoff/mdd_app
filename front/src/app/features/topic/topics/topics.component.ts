import {Component, OnDestroy, OnInit} from '@angular/core';
import {TopicService} from "../../../shared/services/topic.service";
import {User} from "../../../shared/interfaces/user.interface";
import {UserService} from "../../../shared/services/user.service";
import {Topic} from "../../../shared/interfaces/topic.interface";
import {mergeMap, Subscription} from "rxjs";
import {ButtonComponent} from "../../../core/components/button/button.component";
import {
  logExperimentalWarnings
} from "@angular-devkit/build-angular/src/builders/browser-esbuild/experimental-warnings";

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit, OnDestroy {
  topics: Topic[] = [];
  topic$!: Subscription;
  subscribe$!: Subscription;
  user!: User;

  constructor(private topicService: TopicService, private userService: UserService){}

  ngOnInit(): void {
    this.topic$ = this.userService.getMe().pipe(
      mergeMap(result => {
        this.user = result.data.me;
        return this.topicService.getTopics();
      }))
      .subscribe({
        next: (result) => {
          this.topics = result.data.Topics
          this.user.idTopic = this.user.topicsList!.map(topic => topic.id)
        }
      })
  }

  ngOnDestroy(): void {
    this.topic$.unsubscribe();
    if (this.subscribe$)
      this.subscribe$.unsubscribe();
  }

  onClick(id : string, appButton : ButtonComponent) : void {

    if (this.user.idTopic?.includes(id))
      return;
    this.user.idTopic?.push(id);
    appButton.label = "Abonné";
    this.subscribe$ = this.userService.subscribe(id).pipe(
      mergeMap(_ => {return this.userService.getMe()})
    ).subscribe({
      next: (result: {data: {me: User}}) => {
        this.user = result.data.me
        this.user.idTopic = this.user.topicsList!.map(topic => topic.id)
      },
      error:(err => {
        console.log(err)
      })
    });
  }

}
