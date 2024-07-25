import {Topic} from "./topic.interface";

export interface User {
  id?: string
  email?: string
  pseudo?: string
  idComment?: string[]
  //idPost?: string[]
  idTopic?: string[]
  topicsList?: Topic[]
  created_at?: Date
  updated_at?: Date

}
